import numpy as np
import pandas as pd
from flask import Flask,request,jsonify
import pickle
import requests,ssl
from flask_cors import CORS
app=Flask(__name__)
rfregressor=pickle.load(open('model.pkl','rb'))
CORS(app)
visibility_item_avg = [[]]
def impute_visibility_mean(cols):
    visibility = cols[0]
    item = cols[1]
    
    if visibility == 0:
       
        return visibility_item_avg['Item_Visibility'][visibility_item_avg.index == item]
    else:
        return visibility

@app.route('/findsales',methods=['POST'])
def findSales():
    
    print(request)
    item_identifier=request.json['Item_Identifier']
    
    item_weight=float(request.json['Item_Weight'])
	
    
    item_fat_content=float(request.json['Item_Fat_Content'])
	
    
    item_visibility=float(request.json['Item_Visibility'])
    
    item_type=request.json['Item_Type']
    
    item_mrp=float(request.json['Item_MRP'])
    
    outlet_identifier=request.json['Outlet_Identifier']
    
    outlet_establishment_year=int(request.json['Outlet_Establishment_Year'])
    
    outlet_size=request.json['Outlet_Size']
    
    outlet_location_type=request.json['Outlet_Location_Type']
    
    outlet_type=request.json['Outlet_Type']
    

    datavalues=[[item_identifier,item_weight,item_fat_content,item_visibility,item_type,item_mrp,outlet_identifier,outlet_establishment_year,outlet_size,outlet_location_type,outlet_type]]
    data = pd.DataFrame(datavalues, columns = ['Item_Identifier', 'Item_Weight', 'Item_Fat_Content','Item_Visibility','Item_Type', 'Item_MRP', 'Outlet_Identifier','Outlet_Establishment_Year','Outlet_Size','Outlet_Location_Type','Outlet_Type'])
    visibility_item_avg = data.pivot_table(values='Item_Visibility',index='Item_Identifier')
    data['Item_Visibility'] = data[['Item_Visibility','Item_Identifier']].apply(impute_visibility_mean,axis=1).astype(float)
    data['Outlet_Years'] = 2013 - data['Outlet_Establishment_Year']
    data['Item_Type_Combined'] = data['Item_Identifier'].apply(lambda x: x[0:2])
  #Rename them to more intuitive categories:
    data['Item_Type_Combined'] = data['Item_Type_Combined'].map({'FD':'Food','NC':'Non-Consumable','DR':'Drinks'})
  
  

    data.loc[data['Item_Type_Combined']=="Non-Consumable",'Item_Fat_Content'] = "Non-Edible"
  
    Mean_Visibility=data['Item_Visibility'].mean()
    data['Item_Visibility_MeanRatio']=data.apply(lambda x:x['Item_Visibility']/Mean_Visibility,axis=1)
  
  #changing all nominal attributes to numerical using label encoder
    from sklearn.preprocessing import LabelEncoder
    le=LabelEncoder()
    data['outlet']=le.fit_transform(data['Outlet_Identifier'])



    var_mod = ['Item_Fat_Content','Outlet_Location_Type','Outlet_Size','Item_Type_Combined','Outlet_Type']

    for i in var_mod:
        data[i] = le.fit_transform(data[i])

    data.drop(['Item_Type','Outlet_Establishment_Year'],axis=1,inplace=True)
  
    data=data.drop(['Outlet_Identifier','Item_Identifier'], axis=1)
    res = rfregressor.predict(data)
    return str(res[0])


if __name__=="__main__":
    app.run(debug=True)
