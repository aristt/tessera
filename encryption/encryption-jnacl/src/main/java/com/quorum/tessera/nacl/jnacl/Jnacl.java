package com.quorum.tessera.nacl.jnacl;

import com.neilalexander.jnacl.NaCl;
import com.quorum.tessera.nacl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;

import static com.neilalexander.jnacl.crypto.curve25519xsalsa20poly1305.*;

/**
 * Uses jnacl, which is a pure Java implementation of the NaCl standard
 */
public class Jnacl implements NaclFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(Jnacl.class);

    private static final String REDACTED = "REDACTED";

    private final SecureRandom secureRandom;

    private final SecretBox secretBox;

    public Jnacl(final SecureRandom secureRandom, final SecretBox secretBox) {
        this.secureRandom = Objects.requireNonNull(secureRandom);
        this.secretBox = Objects.requireNonNull(secretBox);
    }

    @Override
    public Key computeSharedKey(final Key publicKey, final Key privateKey) {
        final byte[] precomputed = new byte[crypto_secretbox_BEFORENMBYTES];

        LOGGER.debug("Computing the shared key for public key {} and private key {}", publicKey, privateKey);
        final int jnaclResult = secretBox.cryptoBoxBeforenm(
            precomputed, publicKey.getKeyBytes(), privateKey.getKeyBytes()
        );

        if(jnaclResult == -1) {
            LOGGER.warn("Could not compute the shared key for pub {} and priv {}", publicKey, REDACTED);
            LOGGER.debug("Could not compute the shared key for pub {} and priv {}", publicKey, privateKey);
            throw new NaclException("JNacl could not compute the shared key");
        }

        final Key sharedKey = new Key(precomputed);

        LOGGER.debug("Computed shared key {} for pub {} and priv {}", sharedKey, publicKey, privateKey);

        return sharedKey;

    }

    @Override
    public byte[] seal(final byte[] message, final Nonce nonce, final Key publicKey, final Key privateKey) {

        LOGGER.debug("Sealing message using public key {}", publicKey);
        LOGGER.debug(
            "Sealing message {} using nonce {}, public key {} and private key {}",
            Arrays.toString(message), nonce, publicKey, privateKey
        );

        try {

            final NaCl nacl = new NaCl(privateKey.getKeyBytes(), publicKey.getKeyBytes());

            final byte[] cipherText = nacl.encrypt(message, nonce.getNonceBytes());

            LOGGER.debug("Created sealed payload for public key {}", publicKey);
            LOGGER.debug(
                "Created sealed payload {} using nonce {}, public key {} and private key {}",
                Arrays.toString(cipherText), nonce, publicKey, privateKey
            );

            return extract(cipherText, crypto_secretbox_BOXZEROBYTES);

        } catch (final Exception ex) {
            throw new NaclException(ex.getMessage());
        }

    }

    @Override
    public byte[] open(final byte[] cipherText, final Nonce nonce, final Key publicKey, final Key privateKey) {
        LOGGER.debug("Opening message using public key {}", publicKey);
        LOGGER.debug(
            "Opening message {} using nonce {}, public key {} and private key {}",
            Arrays.toString(cipherText), nonce, publicKey, privateKey
        );

        try {

            final byte[] paddedInput = pad(cipherText, crypto_secretbox_BOXZEROBYTES);

            final NaCl nacl = new NaCl(privateKey.getKeyBytes(), publicKey.getKeyBytes());

            final byte[] plaintext = nacl.decrypt(paddedInput, nonce.getNonceBytes());

            LOGGER.debug("Created sealed payload for public key {}", publicKey);
            LOGGER.debug(
                "Created sealed payload {} using nonce {}, public key {} and private key {}",
                Arrays.toString(cipherText), nonce, publicKey, privateKey
            );

            return plaintext;
        } catch (final Exception ex) {
            throw new NaclException(ex.getMessage());
        }
    }

    @Override
    public byte[] sealAfterPrecomputation(final byte[] message, final Nonce nonce, final Key sharedKey) {

        final byte[] paddedMessage = new byte[message.length + crypto_secretbox_ZEROBYTES];
        final byte[] output = new byte[message.length + crypto_secretbox_ZEROBYTES];

        LOGGER.debug("Sealing message using public key {}", sharedKey);
        LOGGER.debug(
            "Sealing message {} using nonce {} and shared key {}",
            Arrays.toString(message), nonce, sharedKey
        );

        System.arraycopy(message, 0, paddedMessage, crypto_secretbox_ZEROBYTES, message.length);
        final int jnaclResult = secretBox.cryptoBoxAfternm(
            output, paddedMessage, paddedMessage.length, nonce.getNonceBytes(), sharedKey.getKeyBytes()
        );

        if(jnaclResult == -1) {
            LOGGER.warn("Could not create sealed payload using shared key {}", sharedKey);
            LOGGER.debug("Could not create sealed payload using shared key {}", sharedKey);
            throw new NaclException("jnacl could not seal the payload using the shared key");
        }

        LOGGER.debug("Created sealed payload for shared key {}", sharedKey);
        LOGGER.debug(
            "Created sealed payload {} using nonce {} and shared key {}",
            Arrays.toString(output), nonce, sharedKey
        );

        return extract(output, crypto_secretbox_BOXZEROBYTES);
    }

    @Override
    public byte[] openAfterPrecomputation(final byte[] cipherText, final Nonce nonce, final Key sharedKey) {
        LOGGER.debug("Opening message using shared key {}", sharedKey);
        LOGGER.debug(
            "Opening message {} using nonce {} and shared key {}",
            Arrays.toString(cipherText), nonce, sharedKey
        );

        final byte[] paddedInput = pad(cipherText, crypto_secretbox_BOXZEROBYTES);
        final byte[] paddedOutput = new byte[paddedInput.length];

        final int jnaclResult = secretBox.cryptoBoxOpenAfternm(
            paddedOutput, paddedInput, paddedInput.length, nonce.getNonceBytes(), sharedKey.getKeyBytes()
        );

        if(jnaclResult == -1) {
            LOGGER.warn("Could not open sealed payload using shared key {}", sharedKey);
            LOGGER.debug("Could not open sealed payload using shared key {}", sharedKey);
            throw new NaclException("jnacl could not open the payload using the shared key");
        }

        LOGGER.debug("Opened sealed payload for shared key {}", sharedKey);
        LOGGER.debug(
            "Opened payload {} using nonce {}, public key {} and private key {} to get result {}",
            Arrays.toString(cipherText), nonce, sharedKey, REDACTED, Arrays.toString(paddedOutput)
        );

        return extract(paddedOutput, crypto_secretbox_ZEROBYTES);
    }

    @Override
    public Nonce randomNonce() {
        final byte[] nonceBytes = new byte[crypto_secretbox_NONCEBYTES];

        this.secureRandom.nextBytes(nonceBytes);

        final Nonce nonce = new Nonce(nonceBytes);

        LOGGER.debug("Generated random nonce {}", nonce);

        return nonce;
    }

    @Override
    public KeyPair generateNewKeys() {
        final byte[] publicKey = new byte[crypto_secretbox_PUBLICKEYBYTES];
        final byte[] privateKey = new byte[crypto_secretbox_SECRETKEYBYTES];

        LOGGER.info("Generating new keypair...");

        final int jnaclResult = secretBox.cryptoBoxKeypair(publicKey, privateKey);

        if(jnaclResult == -1) {
            LOGGER.warn("Unable to generate a new keypair!");
            throw new NaclException("jnacl could not generate a new public/private keypair");
        }

        final Key pubKey = new Key(publicKey);
        final Key privKey = new Key(privateKey);

        LOGGER.info("Generated public key {} and private key {}", pubKey, REDACTED);
        LOGGER.debug("Generated public key {} and private key {}", pubKey, privKey);

        return new KeyPair(pubKey, privKey);
    }

    @Override
    public Key createSingleKey() {
        LOGGER.debug("Generating random key");

        final byte[] keyBytes = new byte[crypto_secretbox_PUBLICKEYBYTES];

        this.secureRandom.nextBytes(keyBytes);

        final Key key = new Key(keyBytes);

        LOGGER.debug("Random key generated");
        LOGGER.debug("Generated key with value {}", key);

        return key;
    }

    /**
     * Left-pads a given message with padSize amount of zeros
     *
     * @param input the message to be padded
     * @param padSize the amount of left-padding to apply
     * @return the padded message
     */
    private byte[] pad(final byte[] input, final int padSize) {
        final byte[] paddedMessage = new byte[padSize + input.length];
        System.arraycopy(input, 0, paddedMessage, padSize, input.length);

        return paddedMessage;
    }

    /**
     * Removes left-padding from a given message to tune of padSize
     *
     * @param input The message from which to remove left-padding
     * @param padSize The amount of left-padding to remove
     * @return The trimmed message
     */
    private byte[] extract(final byte[] input, final int padSize) {
        final byte[] extractedMessage = new byte[input.length - padSize];
        System.arraycopy(input, padSize, extractedMessage, 0, extractedMessage.length);

        return extractedMessage;
    }

}
