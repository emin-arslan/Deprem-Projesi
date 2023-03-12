from copyreg import constructor
import pymongo
from bson.objectid import ObjectId
import certifi

class Veritabani:
    def __init__(self):
        ca = certifi.where()
        client = pymongo.MongoClient("connection")
        db = client.test
        mydb = client["myFirstDatabase"]
        self.mycol = mydb["Apartmanlar"]
    def guncelle(self,id,count):
        myquery = { "_id": ObjectId(id) }
        newvalues = { "$set": { "People_count": count} }
        self.mycol.update_one(myquery, newvalues)
    def select(self,id):
        Apartman=self.mycol.find_one({"_id": ObjectId(id)})
        print(Apartman)


#deneme=veritabani()
#deneme.guncelle("61f54a61aebb8a12dcceac0b",9)
#deneme.select("61f54a61aebb8a12dcceac0b")








#mydict = { "name": "EminApartmani", "Long": "38.4","Lat":"27.12 ","People_count":"10"}
#x = mycol.insert_one(mydict).inserted_id
#print(type(x))



#Apartman=mycol.find_one({"_id": ObjectId("61f54a61aebb8a12dcceac0b")})
#print(Apartman)


