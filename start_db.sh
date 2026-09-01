#!/bin/bash

psql -h localhost -U postgres <<EOF
CREATE DATABASE register;
EOF

psql -h localhost -U postgres -d register <<EOF
\i /workspace/nd035-c1-spring-boot-basics-project-starter/starter/cloudstorage/src/main/resources/schema.sql
\dt
EOF

# Stay connected to the database
psql -h localhost -U postgres -d register