import warnings
warnings.filterwarnings('ignore')
import pandas as pd
import numpy as np
import pickle
train=pd.read_csv('Train.csv')
test = pd.read_csv("Test.csv")
train['source']='train'
test['source']='test'
data = pd.concat([train, test],ignore_index=True)
data.Item_Weight.fillna(train.Item_Weight.mean(),inplace=True)
data.Outlet_Size.fillna(data.Outlet_Size.value_counts().index[0],inplace=True)
visibility_item_avg = data.pivot_table(values='Item_Visibility',index='Item_Identifier')

def impute_visibility_mean(cols):
    visibility = cols[0]
    item = cols[1]
    
    if visibility == 0:
       
        return visibility_item_avg['Item_Visibility'][visibility_item_avg.index == item]
    else:
        return visibility

data['Item_Visibility'] = data[['Item_Visibility','Item_Identifier']].apply(impute_visibility_mean,axis=1).astype(float)
data['Outlet_Years'] = 2013 - data['Outlet_Establishment_Year']
data['Item_Type_Combined'] = data['Item_Identifier'].apply(lambda x: x[0:2])
#Rename them to more intuitive categories:
data['Item_Type_Combined'] = data['Item_Type_Combined'].map({'FD':'Food','NC':'Non-Consumable','DR':'Drinks'})
data['Item_Fat_Content'] = data['Item_Fat_Content'].replace({"LF":"Low Fat","reg":"Regular","low fat":"Low Fat"})
x=data['Item_Type_Combined']=="Non-Consumable"

data.loc[data['Item_Type_Combined']=="Non-Consumable",'Item_Fat_Content'] = "Non-Edible"
Mean_Visibility=data['Item_Visibility'].mean()
data['Item_Visibility_MeanRatio']=data.apply(lambda x:x['Item_Visibility']/Mean_Visibility,axis=1)
from sklearn.preprocessing import LabelEncoder
le=LabelEncoder()
data['outlet']=le.fit_transform(data['Outlet_Identifier'])



var_mod = ['Item_Fat_Content','Outlet_Location_Type','Outlet_Size','Item_Type_Combined','Outlet_Type']

for i in var_mod:
    data[i] = le.fit_transform(data[i])
data.drop(['Item_Type','Outlet_Establishment_Year'],axis=1,inplace=True)
train = data.loc[data['source']=="train"]
test = data.loc[data['source']=="test"]
test.drop(['Item_Outlet_Sales','source'],axis=1,inplace=True)
train.drop(['source'],axis=1,inplace=True)
#saving modified files
train.to_csv("train_modified.csv",index=False)
test.to_csv("test_modified.csv",index=False)
#splitting
trainm = pd.read_csv("train_modified.csv")
testm = pd.read_csv("test_modified.csv")
XTrain=trainm.drop(['Item_Outlet_Sales','Outlet_Identifier','Item_Identifier'], axis=1)
YTrain=trainm['Item_Outlet_Sales']
XTest=testm.drop(['Outlet_Identifier','Item_Identifier'], axis=1)
#Random Forest Model
from sklearn.ensemble import RandomForestRegressor
rfregressor = RandomForestRegressor(n_estimators=100,max_depth=6, min_samples_leaf=50,n_jobs=4)
rfregressor.fit(XTrain, YTrain)
# Saving model to disk
pickle.dump(rfregressor, open('model.pkl','wb'))



