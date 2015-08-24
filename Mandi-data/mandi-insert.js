var mongoClient = require('mongodb').MongoClient;
var fs= require('fs');

var mongoDbUrl =  'mongodb://localhost:27017/mandi';
var collectionName = 'user';
var jsonObject;

fs.readFile('data.json', 'utf8', function(err, data) {
	if(err)
		console.log(err);
	else {
		jsonObject = JSON.parse(data);
	}
})

mongoClient.connect(mongoDbUrl, function(err, db) {
	if(!err) {
		console.log('Connected to mongoDB');
        insertDocument(db, collectionName, jsonObject)       
	}
})

var endConnection = function(db) {
        	db.close();
        	console.log("connection closed");
        }; 

var insertDocument = function(db, collectionName, document) {
	var collection = db.collection(collectionName);
	var counter = 0;
	for(var i in document) {
		collection.insert(document[i], function(err, result) {
		counter++;	
		if(err){
			console.log(err);
		}
		else {
			console.log(result);
		}
		if(counter == document.length) {
			endConnection(db);
		}
	})
	}
}
