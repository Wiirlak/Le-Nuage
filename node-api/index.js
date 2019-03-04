//Import express
let express = require('express');
//Import Body parser
let bodyParser = require('body-parser');
//Import Mongoose
let mongoose = require('mongoose');
//Import Cors
let cors = require('cors');
//Initialize the app
let app = express();

//Import routes
let apiRoutes = require("./api-routes");

//Configure bodyparser to handle requests
app.use(bodyParser.urlencoded({
    useNewUrlParser: true
}));
app.use(bodyParser.json());
app.use(cors());

//var urlMongo = "mongodb://admin:admin@annuel3al-shard-00-00-iui8c.mongodb.net:27017,annuel3al-shard-00-01-iui8c.mongodb.net:27017,annuel3al-shard-00-02-iui8c.mongodb.net:27017/pierre?ssl=true&replicaSet=annuel3AL-shard-0&authSource=admin&retryWrites=true";
var urlMongo = "mongodb://127.0.0.1:27017";

//Connect to Mongoose and set connection variable
mongoose.connect(urlMongo);

let db = mongoose.connection;
//Setup server port
let port = process.env.PORT || 3000;

//Send message for default URL
app.get('/', (req, res) => res.send('Hello World with Express'));

//Use Apu routes in the App
app.use('/api', apiRoutes);

//Launch app to listen to specified port
app.listen(port, function () {
    console.log("Running Node API on port " + port);
});









