db.createUser({
    user: 'root',
    pwd: 'toor',
    roles: [
        {
            role: 'readWrite',
            db: 'testDB',
        },
    ],
});
db.createCollection('users', {capped: false});

db.users.insert([
    {
        "username": "ragnar777",
        "dni": "VIKI771012HMCRG093",
        "enabled": true,
        "password_not_encrypted": "s3cr3t",
        "password": "$2a$10$FQB/O9zG0JPQcchgyqi6MuvY87Hyx2IonuejD4j4OZoSIgDbHxRoO",
        "role":
            {
                "granted_authorities": ["read"],
            },
    },
    {
        "username": "heisenberg",
        "dni": "BBMB771012HMCRR022",
        "enabled": true,
        "password_not_encrypted": "p4sw0rd",
        "password": "$2a$10$ZPhviq/KeF590A.kpR6qQOqJGEr6ATdDxm1NRUJEFi.n6hN64dZym",
        "role":
            {
                "granted_authorities": ["read"],
            },
    },
    {
        "username": "misterX",
        "dni": "GOTW771012HMRGR087",
        "enabled": true,
        "password_not_encrypted": "misterX123",
        "password": "$2a$10$IsRDsWqMxQti/Dnyu.EeZuyq4WaQ3f7tthJCiHeKKVAZ4fC4Brpke",
        "role":
            {
                "granted_authorities": ["read", "write"],
            },
    },
    {
        "username": "neverMore",
        "dni": "WALA771012HCRGR054",
        "enabled": true,
        "password_not_encrypted": "4dmIn",
        "password": "$2a$10$wtNKWZG/MU6m/flGdoHjFOlhTLfB68XCrn9nz7F9L7v5q4eChIRJu",
        "role":
            {
                "granted_authorities": ["write"],
            },
    },
]);
