var express = require('express');
var cors = require('cors');

var hostname = 'localhost';
var port = 3000;

var mongoose = require('mongoose');

var options = {
    keepAlive: 300000,
    useMongoClient: true
};

var urlMongo = "mongodb://admin:admin@annuel3al-shard-00-00-iui8c.mongodb.net:27017,annuel3al-shard-00-01-iui8c.mongodb.net:27017,annuel3al-shard-00-02-iui8c.mongodb.net:27017/bastien?ssl=true&replicaSet=annuel3AL-shard-0&authSource=admin&retryWrites=true"

// Nous connectons l'API à notre base de données
mongoose.connect(urlMongo, options);

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'Erreur lors de la connexion'));
db.once('open', function (){
    console.log("Connexion à la base OK");
});

// Nous créons un objet de type Express.
var app = express();

var bodyParser = require("body-parser");
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use(cors());



var pommeSchema = mongoose.Schema({
    name: String,
    pepins: String
},
{
    collection:'pomme',
    versionKey: false
});

var Pomme = mongoose.model('Pomme', pommeSchema);

//mise en place de route root
var myRouter = express.Router();
myRouter.route('/')
.all(function(req,res){
      res.json({message : "Bienvenue sur notre Frugal API ", methode : req.method});
})


//2eme route -> listing
myRouter.route('/pommes')
.get(function(req,res){
	Pomme.find(function(err, pommes){
        if (err)
            res.status(404).send('Not found');
        else
            res.json(pommes);
    });
});

//4eme route -> ajout
myRouter.route('/pommes/new')
.post(function(req,res){
    var pomme = new Pomme();
    pomme.name = req.body.name;
    pomme.pepins = req.body.pepins;
    pomme.save(function(err){
        if (err)
            res.status(404).send('Not found');
        else
            res.json({message : 'Bravo, la piscine est maintenant stockée en base de données'});
    });
});

//3eme route -> recuperation d'un id
myRouter.route('/pommes/:pomme_id')
.get(function(req,res){
        Pomme.findById(req.params.pomme_id, function(err, pomme) {
        if (err)
            res.status(404).send('Not found');
        else
            res.json(pomme);
            /*if(res.json(pomme) == null)
                res.json(pomme);
            else{
                res.writeHead(404);
            }*/
        /*res.writeHead(200, {"Content-Type": "text/html"});
        res.write('<!DOCTYPE html>'+
            '<html>'+
            '    <head>'+
            '        <meta charset="utf-8" />'+
            '        <title>Ma page Node.js !</title>'+
            '    </head>'+ 
            '    <body style="background-color : red">'+
            '     	<p>'+pomme._id+'</p>'+
            '     	<p>'+pomme.name+'</p>'+
            '     	<p>'+pomme.pepins+'</p>'+
            '    </body>'+
            '</html>');
        res.end();*/
    });
})
.put(function(req,res){ 
    Pomme.findById(req.params.pomme_id, function(err, pomme) {
        if (err)
            res.status(404).send('Not found');
        else{
            pomme.name = req.body.name;
            pomme.pepins = req.body.pepins;
            pomme.save(function(err){
                if(err){
                    res.send(err);
                }
                res.json({message : 'Bravo, mise à jour des données OK'});
            }); 
        }               
    })
})
.delete(function(req,res){ 
    Pomme.remove({_id: req.params.pomme_id}, function(err, pomme){
        if (err)
            res.status(404).send('Not found');
        else
            res.json({message:"Bravo, piscine supprimée"}); 
    }); 
})

//Indique sur port fonctionné
app.use(myRouter);
app.use(function(req, res) {
    res.status(404).send('Not founddd');
 });
app.listen(port, hostname, function(){
	console.log("Mon serveur fonctionne sur http://"+ hostname +":"+port);
});

