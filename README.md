
customer-client (8080) -------> rsocket (tcp) ------> customer-service ---- r2dbc driver ----> postgresql


1. Start PostgreSQL

```bash
docker run -d --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=secret postgres:10.5
```

2. Start `customer-service` and `customer-client`

3.  Get customers

``` 
http :8080/customers

[
    {
        "name": "Walter White"
    },
    {
        "name": "Jesse Pinkman"
    },
    {
        "name": "Gus Fring"
    },
    {
        "name": "Mike Ehrmantraut"
    },
    {
        "name": "Saul Goodman"
    },
    {
        "name": "Hank Schrader"
    },
    {
        "name": "Skyler White"
    }
]
```