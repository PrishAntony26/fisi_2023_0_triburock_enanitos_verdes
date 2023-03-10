const express = require('express');
const mysql = require('mysql2');
const myconn = require('express-myconnection');

const routes = require('./routes');

const app = express();

app.set('port', process.env.PORT || 8080); //Process.env.port para cuando sea despleagdo


const dbConfig = {
    host: 'cinefilo.mysql.database.azure.com',
    port: 3306,
    user: 'xaxi',
    password: 'EnanitosVerdesG5',
    database: 'cinefilo',
    dialect: "mysql",
    ssl: {
        rejectUnauthorized: false
    },
    dialectOptions: {
       ssl: {
          require: true
       }
     }
};

app.use(myconn(mysql, dbConfig, 'single'));

app.use('/cineapp', routes);

app.use('/cineapp', express.static('resources/images'))

app.listen(app.get('port'), ()=>{
    console.log('Server running on port', app.get('port'));
})
