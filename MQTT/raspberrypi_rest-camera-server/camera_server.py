import datetime
import os
from flask import Flask, request,jsonify
from flask_restful import Resource, Api
from werkzeug.utils import secure_filename
import sqlite3
from sqlalchemy.sql.expression import func

from sqlalchemy import Column, Integer, Text, DateTime, create_engine, LargeBinary
from sqlalchemy.orm import scoped_session, sessionmaker
from sqlalchemy.ext.declarative import declarative_base

from dataclasses import dataclass

ALLOWED_EXTENSIONS = {'txt', 'pdf', 'png', 'jpg', 'jpeg', 'gif'}

app = Flask(__name__)
api = Api(app)

Base = declarative_base()
metadata = Base.metadata
engine = create_engine(r'sqlite:///D:\Apps\SQLite\images.sqlite3')
db_session = scoped_session(sessionmaker(autocommit=True, autoflush=True, bind=engine))
Base.query = db_session.query_property

class Image(Base):
    __tablename__ = "Images"
    
    ID = Column(Integer, primary_key=True)
    TITLE = Column(Text, nullable=False)
    DEVICE = Column(Text, nullable=False)
    DATE = Column(DateTime, nullable=False)
    PICTURE = Column(Text, nullable=False)
    EXTENSIONS = Column(Text, nullable=False)
    DESCRIPTION = Column(Text)
    
    def serialize(self):
        return{
            "ID" : str(self.ID),
            "TITLE" : self.TITLE,
            "DEVICE" : self.DEVICE,
            "DATE" : str(self.DATE),
            "PICTURE" : self.PICTURE,
            "EXTENSIONS" : self.EXTENSIONS,
            "DESCRIPTION" : self.DESCRIPTION        
        }
        
def file_check(filename):
    return '.' in filename and \
        filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS
        
class File(Resource):
    def get(self, id):
        image = Image.query.get(id)
        if not image:
            return jsonify({"message" : "does not exist"})
        return image.serialize()
    def delete(self, id):
        image = Image.query.get(id)
        if not image:
            return jsonify({"message" : "file with this id does not exist %s" %id})
        db_session.delete(image)
        db_session.flush()
        return jsonify({"message" : "%s deleted" %id})
    def put(self, id):
        if request.form["EXTENSION"] not in ALLOWED_EXTENSIONS:
            return jsonify({"message" : "extension is wrong"})
        image = Image(TITLE=request.form["TITLE"], DEVICE=request.form["DEVICE"], DATE=datetime.datetime.strptime(request.form["DATE"], '%Y-%m-%d %H:%M:%S.%f'), PICTURE=request.form["PICTURE"], EXTENSIONS=request.form["EXTENSIONS"], DESCRIPTION=request.form["DESCRIPTION"])
        db_session.add(image)
        db_session.flush()
        return jsonify({"message" : "file saved"})

api.add_resource(File, "/file/<string:id>")

def createDB():
    Base.metadata.create_all(bind = engine)
    
if __name__ == '__main__':
    createDB()
    app.run(debug=True, host="0.0.0.0")