# Contact API Spec

## Create Contact

Endpoint : POST /api/contacts

Request Header :

- X-API-TOKEN : Token (Wajib)

Request Body :

```json
{
  "firstName" : "ahmad haris",
  "lastName" : "kurniawan",
  "email" : "haris@haris.com",
  "phone" : "08123456789"
}
```

Response Body (Success) :

```json
{
  "data": {
    "id" : "random string",
    "firstName" : "ahmad haris",
    "lastName" : "kurniawan",
    "email" : "haris@haris.com",
    "phone" : "08123456789"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "email format tidak sesuai" // contoh
}
```

## Update Contact

Endpoint : PUT /api/contacts/{idContact}

Request Header :

- X-API-TOKEN : Token (Wajib)

Request Body :

```json
{
  "firstName" : "ahmad haris",
  "lastName" : "kurniawan",
  "email" : "haris@haris.com",
  "phone" : "08123456789"
}
```

Response Body (Success) :

```json
{
  "data": {
    "id" : "random string",
    "firstName" : "ahmad haris",
    "lastName" : "kurniawan",
    "email" : "haris@haris.com",
    "phone" : "08123456789"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "email format tidak sesuai" // contoh
}
```

## Get Contact

Endpoint : GET /api/contacts/{idContact}

Request Header :

- X-API-TOKEN : Token (Wajib)

Response Body (Success) :

```json
{
  "data": {
    "id" : "random string",
    "firstName" : "ahmad haris",
    "lastName" : "kurniawan",
    "email" : "haris@haris.com",
    "phone" : "08123456789"
  }
}
```

Response Body (Failed, 404) :

```json
{
  "errors" : "kontak tidak ditemukan"
}
```

## Search Contact

Endpoint : GET /api/contacts/

Query Param :

- name : String, contact firstName or contact lastName, menggunakan like query (optional)
- phone : String, contact phone, menggunakan like query (optional)
- email : String, contact email, menggunakan like query (optional)
- page : Integer, start from 0, default 0
- size : Integer, default 10

Request Header :

- X-API-TOKEN : Token (Wajib)

Response Body (Success) :

```json
{
  "data" : [
    {
      "id" : "random string",
      "firstName" : "ahmad haris",
      "lastName" : "kurniawan",
      "email" : "haris@haris.com",
      "phone" : "08123456789"
    },
    {
      "id" : "random string",
      "firstName" : "ahmad haris",
      "lastName" : "kurniawan",
      "email" : "haris@haris.com",
      "phone" : "08123456789"
    }
  ],
  "paging" : {
    "currentPage" : 0,
    "totalPage" : 13,
    "size" : 10
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Unauthorized" // contoh
}
```

## Remove Contact

Endpoint : DELETE /api/contacts/{idContact}

Request Header :

- X-API-TOKEN : Token (Wajib)

Response Body (Success) :

```json
{
  "data" : "berhasil menghapus data"
}
```

Response Body (Failed 404(contoh)) :

```json
{
  "data" : "kontak tidak ditemukan"
}
```