'use strict';
require('dotenv').config();

//Import express
const express = require('express');
//Import Cors
const cors = require('cors');
//loger
const morgan = require('morgan');
//Import routes
const RouterBuilder = require('./routes');
//Initialize the app
const app = express();

//DB config
//const config = require('./config/config.json')['development'];
//const config = require('./config/config.json')['production'];

//Configure bodyparser to handle requests
app.use(morgan('dev'));
app.use(cors());

RouterBuilder.build(app);

//var urlMongo = "mongodb://admin:admin@annuel3al-shard-00-00-iui8c.mongodb.net:27017,annuel3al-shard-00-01-iui8c.mongodb.net:27017,annuel3al-shard-00-02-iui8c.mongodb.net:27017/pierre?ssl=true&replicaSet=annuel3AL-shard-0&authSource=admin&retryWrites=true";
//var urlMongo = config.dialect + '://' + config.host + ':' + config.port + '/' + config.database;

//Connect to Mongoose and set connection variable
//mongoose.connect(urlMongo, { useNewUrlParser: true});

//et db = mongoose.connection;
//Setup server port
let port = process.env.PORT || 3000;

//Send message for default URL
//app.get('/', (req, res) => res.send('Hello World with Express'));

//Use Apu routes in the App
//app.use('/api', apiRoutes);

//Launch app to listen to specified port
app.listen(port, () => console.log(`Listening on ${port}...`));









