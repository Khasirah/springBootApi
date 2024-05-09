# Address API Spec

## Create Address

Endpoint : POST /api/contact/{idContact}/addresses

Request Header :

- X-API-TOKEN : Token (Wajib)

Request Body :

```json
{
  "street" : "jalan jalan",
  "city" :  "kota",
  "province" :  "provinsi",
  "country" : "negara",
  "postalCode" : "12345"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id" : "randomg kode",
    "street" : "jalan jalan",
    "city" :  "kota",
    "province" :  "provinsi",
    "country" : "negara",
    "postalCode" : "12345"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "gagal input data"
}
```

## Update Address

Endpoint : PUT /api/contact/{idContact}/addresses/{idAddress}

Request Header :

- X-API-TOKEN : Token (Wajib)

Request Body :

```json
{
  "street" : "jalan jalan",
  "city" :  "kota",
  "province" :  "provinsi",
  "country" : "negara",
  "postalCode" : "12345"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id" : "randomg kode",
    "street" : "jalan jalan",
    "city" :  "kota",
    "province" :  "provinsi",
    "country" : "negara",
    "postalCode" : "12345"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "alamat tidak ditemukan"
}
```

## Get Address

Endpoint : GET /api/contacts/{idContact}/Addresses/{idAddress}

Request Header :

- X-API-TOKEN : Token (Wajib)

Response Body (Success) :

```json
{
  "data" : {
    "id" : "randomg kode",
    "street" : "jalan jalan",
    "city" :  "kota",
    "province" :  "provinsi",
    "country" : "negara",
    "postalCode" : "12345"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "alamat tidak ditemukan"
}
```

## Remove Address

Endpoint : DELETE /api/contacts/{idContact}/addresses/{idAddress}

Request Header :

- X-API-TOKEN : Token (Wajib)

Response Body (Success) :

```json
{
  "data" : "berhasil menghapus alamat"
}
```

Response Body (Failed) :

```json
{
  "data" : "alamat tidak ditemukan"
}
```

## List Address

Endpoint : GET /api/contacts/{idContact}/addresses

Request Header :

- X-API-TOKEN : Token (Wajib)

Response Body (Success) :

```json
{
  "data" : [
    {
      "id" : "randomg kode",
      "street" : "jalan jalan",
      "city" :  "kota",
      "province" :  "provinsi",
      "country" : "negara",
      "postalCode" : "12345"
    }},
    {
      "id" : "randomg kode",
      "street" : "jalan jalan",
      "city" :  "kota",
      "province" :  "provinsi",
      "country" : "negara",
      "postalCode" : "12345"
    }
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "kontak tidak ditemukan"
}
```