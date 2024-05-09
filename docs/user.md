# User API Spec

## Register User

Endpoint: POST /api/users

Request Body :

```json
{
  "username" : "haris",
  "password" : "rahasia",
  "name" : "haris"
}
```

Response Body (Success) : 

```json
{
  "data" : "Username berhasil terdaftar"
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Username tidak boleh kosong, ???"
}
```

## Login User

Endpoint: POST /api/auth/login

Request Body :

```json
{
  "username" : "haris",
  "password" : "rahasia"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "token" : "TOKEON",
    "expiredAt" : 2312934 // milliseconds
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Username atau Password salah"
}
```

## Get User

Endpoint: GET /api/users/current

Request Header :

- X-API-TOKEN : Token (Wajib)

Response Body (Success) :

```json
{
  "data" : {
    "username" : "haris",
    "name" : "haris"
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Unauthorized"
}
```

## Update User

Endpoint: PATCH /api/users/current

Request Header :

- X-API-TOKEN : Token (Wajib)

Request Body :

```json
{
  "password" : "rahasiaBaru", // put ketika hanya ingin update password
  "name" : "kurniawan" // put ketika hanya ingin update name
}
```

Response Body (Success) :

```json
{
  "data" : {
    "username" : "haris",
    "name" : "kurniawan"
  }
}
```

Response Body (Failed, 401) :

```json
{
  "errors" : "Unauthorized"
}
```

## Logout User

Endpoint: DELETE /api/auth/logout

Request Header :

- X-API-TOKEN : Token (Wajib)

Response Body (Success) :
```json
{
  "data" : "Berhasil keluar"
}
```