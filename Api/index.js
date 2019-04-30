'use strict';
require('dotenv').config();

const express = require('express');
const morgan = require('morgan');
const RouterBuilder = require('./routes');

const app = express();
app.use(morgan('dev'));

RouterBuilder.build(app);

const port = process.env.PORT || 3000;
app.listen(port, () => console.log(`Fortnite starting on port ${port}...`));

const MongoClient = require('mongodb').MongoClient;
const uri = "mongodb+srv://" + process.env.DB_USER + ":" + process.env.DB_PASS + "@annuel3al-iui8c.mongodb.net/test?retryWrites=true";
const client = new MongoClient(uri, { useNewUrlParser: true });
client.connect(err => {
  const collection = client.db("annuel3AL").collection("bastien");
  // perform actions on the collection object
  console.log(collection);
  client.close();
});
