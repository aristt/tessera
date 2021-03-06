
#Tessera rest


## HTTP | HTTPS://localhost:8080/






**Version** 1.0-SNAPSHOT

[**Terms of Service**]()












# APIs


## /api


### GET

<a id="api"></a>









#### Request



##### Parameters






#### Response

**Content-Type: ** application/json, text/html


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | Returns json or html openapi document |  - |

















## /delete






### POST

-deprecated-
<a id="delete">Delete key provided in request. Deprecated: Deletes will be done with DELETE http method</a>









#### Request


**Content-Type: ** application/json

##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>deleteRequest</th>
    <td>body</td>
    <td>yes</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/DeleteRequest">DeleteRequest</a> 
    </td>

</tr>


</table>



#### Response

**Content-Type: ** text/plain


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | Status message |  - |
| 404    | If the entity doesn&#x27;t exist |  - |














## /partyinfo






### POST


<a id="partyInfo"></a>









#### Request


**Content-Type: ** application/octet-stream

##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>body</th>
    <td>body</td>
    <td>yes</td>
    <td></td>
    <td> - </td>

    <td>
    Array[<a href=""></a>]
    
    </td>

</tr>


</table>



#### Response

**Content-Type: ** application/octet-stream


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | Endcoded PartyInfo Data |  - |














## /push






### POST


<a id="push"></a>









#### Request


**Content-Type: ** application/octet-stream

##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>payload</th>
    <td>body</td>
    <td>yes</td>
    <td>Key data to be stored.</td>
    <td> - </td>

    <td>
    Array[<a href=""></a>]
    
    </td>

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 201    | Key created status |  - |
| 500    | General error |  - |














## /receive


### GET
-deprecated-
<a id="receive"></a>









#### Request


**Content-Type: ** application/json

##### Parameters






#### Response

**Content-Type: ** application/json


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| default    | successful operation |  - |

















## /receiveraw


### GET

<a id="receiveRaw">Summit keys to retrieve payload and decrypt it</a>









#### Request


**Content-Type: ** application/octet-stream

##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>c11n-key</th>
    <td>header</td>
    <td>yes</td>
    <td>Encoded Sender Public Key</td>
    <td> - </td>

    
            <td>string </td>
    

</tr>

<tr>
    <th>c11n-to</th>
    <td>header</td>
    <td>no</td>
    <td>Encoded Recipient Public Key</td>
    <td> - </td>

    
            <td>string </td>
    

</tr>


</table>



#### Response

**Content-Type: ** application/octet-stream


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | Raw payload |  - |

















## /resend






### POST


<a id="resend"></a>









#### Request


**Content-Type: ** application/json

##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>resendRequest</th>
    <td>body</td>
    <td>yes</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/ResendRequest">ResendRequest</a> 
    </td>

</tr>


</table>



#### Response

**Content-Type: ** text/plain


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | Encoded payload when TYPE is INDIVIDUAL |  - |
| 500    | General error |  - |














## /send






### POST


<a id="send"></a>









#### Request


**Content-Type: ** application/json

##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>sendRequest</th>
    <td>body</td>
    <td>yes</td>
    <td></td>
    <td> - </td>

    <td>
    
    <a href="#/definitions/SendRequest">SendRequest</a> 
    </td>

</tr>


</table>



#### Response

**Content-Type: ** application/json


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | Send response | <a href="#/definitions/SendResponse">SendResponse</a>|
| 400    | For unknown and unknown keys |  - |














## /sendraw






### POST


<a id="sendRaw"></a>









#### Request


**Content-Type: ** application/octet-stream

##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>c11n-from</th>
    <td>header</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    
            <td>string </td>
    

</tr>

<tr>
    <th>c11n-to</th>
    <td>header</td>
    <td>no</td>
    <td></td>
    <td> - </td>

    
            <td>string </td>
    

</tr>


</table>



#### Response

**Content-Type: ** text/plain


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | Encoded Key |  - |
| 500    | Unknown server error |  - |














## /transaction/{hash}


### GET

<a id="receive"></a>









#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>hash</th>
    <td>path</td>
    <td>yes</td>
    <td>Encoded hash used to decrypt the payload</td>
    <td> - </td>

    
            <td>string </td>
    

</tr>

<tr>
    <th>to</th>
    <td>query</td>
    <td>no</td>
    <td>Encoded recipient key</td>
    <td> - </td>

    
            <td>string </td>
    

</tr>


</table>



#### Response

**Content-Type: ** application/json


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | Receive Response object | <a href="#/definitions/ReceiveResponse">ReceiveResponse</a>|

















## /transaction/{key}








### DELETE

<a id="deleteKey"></a>









#### Request



##### Parameters

<table border="1">
    <tr>
        <th>Name</th>
        <th>Located in</th>
        <th>Required</th>
        <th>Description</th>
        <th>Default</th>
        <th>Schema</th>
    </tr>



<tr>
    <th>key</th>
    <td>path</td>
    <td>yes</td>
    <td>Encoded hash</td>
    <td> - </td>

    
            <td>string </td>
    

</tr>


</table>



#### Response




| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 204    | Successful deletion |  - |
| 404    | If the entity doesn&#x27;t exist |  - |











## /upcheck


### GET

<a id="upCheck"></a>









#### Request



##### Parameters






#### Response

**Content-Type: ** text/plain


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | I&#x27;m up! | |

















## /version


### GET

<a id="getVersion"></a>









#### Request



##### Parameters






#### Response

**Content-Type: ** text/plain


| Status Code | Reason      | Response Model |
|-------------|-------------|----------------|
| 200    | Current application version  | |


















# Definitions

## <a name="/definitions/DeleteRequest">DeleteRequest</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>Encoded public key</td>
            <td>
                
                    
                    string
                
            </td>
            <td>required</td>
            <td>-</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/ReceiveResponse">ReceiveResponse</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>payload</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>Encode response servicing recieve requests</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/ResendRequest">ResendRequest</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>type</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>Resend type INDIVIDUAL or ALL</td>
            <td></td>
        </tr>
    
        <tr>
            <td>publicKey</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>TODO: Define this publicKey, what is it?</td>
            <td></td>
        </tr>
    
        <tr>
            <td>key</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>TODO: Define this key, what is it?</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/SendRequest">SendRequest</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>payload</td>
            <td>
                
                    
                    string
                
            </td>
            <td>required</td>
            <td>Encyrpted payload to send to other parties.</td>
            <td></td>
        </tr>
    
        <tr>
            <td>from</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>Sender public key</td>
            <td></td>
        </tr>
    
        <tr>
            <td>to</td>
            <td>
                
                
                array[string]
                
            </td>
            <td>optional</td>
            <td>Recipient public keys</td>
            <td></td>
        </tr>
    
</table>

## <a name="/definitions/SendResponse">SendResponse</a>

<table border="1">
    <tr>
        <th>name</th>
        <th>type</th>
        <th>required</th>
        <th>description</th>
        <th>example</th>
    </tr>
    
        <tr>
            <td>key</td>
            <td>
                
                    
                    string
                
            </td>
            <td>optional</td>
            <td>TODO: Define this key as something</td>
            <td></td>
        </tr>
    
</table>

