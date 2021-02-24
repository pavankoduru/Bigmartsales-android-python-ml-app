package com.pavan.bigmartsalesdata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String ItemtypeString[]={"Fruits and Vegetables","Snack Foods","Household","Frozen Foods","Dairy","Canned","Baking Goods","Health and Hygiene","Soft Drinks","Meat","Breads","Hard Drinks","Others","Starchy Foods","Breakfast","Seafood"};
    EditText ItemId,Itemweight,ItemVisibility,ItemMrp,OutletId,Establishmentyear;
    String FatContent,OutLetSize,OutletLocation,OutletType,Itemidstr,OutletIdstr,ItemType;
    String Itemvis;
    String Itemmrp;
    String ItemWeight;
    String eyear;
    RadioGroup fatGroup,outletsizeGroup,outletlocationGroup,outlettypeGroup;
    Spinner itemtype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Spinner spino = findViewById(R.id.spinner);
        ItemId=findViewById(R.id.iid);
        Itemweight=findViewById(R.id.iweight);

        ItemVisibility=findViewById(R.id.ivisibility);
        ItemMrp=findViewById(R.id.imrp);
        OutletId=findViewById(R.id.oid);
        Establishmentyear=findViewById(R.id.iyear);
        itemtype=findViewById(R.id.itemtype);
        fatGroup = findViewById(R.id.radioFatContent);
        outletsizeGroup = findViewById(R.id.radiooutletsize);

        outletlocationGroup = findViewById(R.id.radiooutletlocation);
        outlettypeGroup = findViewById(R.id.radiooutletType);


       /* Itemweight.setFilters(new InputFilter[]{new MinMaxFilter(this,0.00,50000)});



        ItemVisibility.setFilters(new InputFilter[]{new MinMaxFilter(this,0.00,0.50)});


        ItemMrp.setFilters(new InputFilter[]{new MinMaxFilter(this,0.09,500)});



        Establishmentyear.setFilters(new InputFilter[]{new MinMaxFilter(this,0,2021)});*/
        Itemidstr=ItemId.getText().toString();
        OutletIdstr=OutletId.getText().toString();
        ItemWeight= Itemweight.getText().toString();
        Itemvis=ItemVisibility.getText().toString();
        Itemmrp=ItemMrp.getText().toString();
        eyear=Establishmentyear.getText().toString();













        itemtype.setOnItemSelectedListener(this);

        // Create the instance of ArrayAdapter
        // having the list of courses

        ArrayAdapter itemtypead
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                ItemtypeString);

        // set simple layout resource file
        // for each item of spinner
        itemtypead.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        itemtype.setAdapter(itemtypead);
    }

    // Performing action when ItemSelected
    // from spinner, Overriding onItemSelected method


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),
                ItemtypeString[i],
                Toast.LENGTH_LONG)
                .show();
        ItemType=ItemtypeString[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void validateAndGet(View view) {
        Itemidstr=ItemId.getText().toString();
        OutletIdstr=OutletId.getText().toString();
        ItemWeight= Itemweight.getText().toString();
        Itemvis=ItemVisibility.getText().toString();
        Itemmrp=ItemMrp.getText().toString();
        eyear=Establishmentyear.getText().toString();
        int selectedId = fatGroup.getCheckedRadioButtonId();
        RadioButton fatcontentradiobutton = findViewById(selectedId);

        int outletsizeidId = outletsizeGroup.getCheckedRadioButtonId();
        RadioButton outletsizeradiobutton = findViewById(outletsizeidId);

        int outletlocationidId = outletlocationGroup.getCheckedRadioButtonId();
        RadioButton outletlocationradiobutton = findViewById(outletlocationidId);

        int outlettypeidId = outlettypeGroup.getCheckedRadioButtonId();
        RadioButton outlettyperadiobutton = findViewById(outlettypeidId);
        if (selectedId == -1) {
            Toast.makeText(MainActivity.this, "Nothing selected please choose any one of the above in Fat", Toast.LENGTH_SHORT).show();
        } else {
            FatContent = fatcontentradiobutton.getText().toString();
        }

        if (outletsizeidId == -1) {
            Toast.makeText(MainActivity.this, "Nothing selected please choose any one of the above in outletsize", Toast.LENGTH_SHORT).show();
        } else {
            OutLetSize = outletsizeradiobutton.getText().toString();
        }

        if (outletlocationidId == -1) {
            Toast.makeText(MainActivity.this, "Nothing selected please choose any one of the above in outletlocation", Toast.LENGTH_SHORT).show();
        } else {
            OutletLocation = outletlocationradiobutton.getText().toString();
        }

        if (outlettypeidId == -1) {
            Toast.makeText(MainActivity.this, "Nothing selected please choose any one of the above in outlettype", Toast.LENGTH_SHORT).show();
        } else {
            OutletType = outlettyperadiobutton.getText().toString();
        }
        Toast.makeText(this, "Itemvis:"+Itemvis+"eyear"+eyear+"Itemmrp"+Itemmrp+"ItemWeight"+ItemWeight+ItemType+OutletIdstr+Itemidstr, Toast.LENGTH_LONG).show();

        boolean ivis = !(TextUtils.isEmpty(Itemvis));
        boolean estyear = !(TextUtils.isEmpty(eyear));
        boolean imrp = !(TextUtils.isEmpty(Itemmrp));
        boolean iw = !(TextUtils.isEmpty(ItemWeight));
        boolean it = !(TextUtils.isEmpty(ItemType));
        boolean oid=!(TextUtils.isEmpty(OutletIdstr));
        boolean iid=!(TextUtils.isEmpty(Itemidstr));
        Toast.makeText(this,
                String.valueOf(ivis)+String.valueOf(estyear)+String.valueOf(imrp)+String.valueOf(iw)+String.valueOf(it)+String.valueOf(oid)+String.valueOf(iid),
                Toast.LENGTH_SHORT).show();



        if ( !(TextUtils.isEmpty(eyear)) && !(TextUtils.isEmpty(Itemvis)) && !(TextUtils.isEmpty(Itemmrp)) && !(TextUtils.isEmpty(ItemWeight))  && !(TextUtils.isEmpty(Itemidstr)) && !(TextUtils.isEmpty(OutletIdstr) && !(TextUtils.isEmpty(ItemType)))) {
            Toast.makeText(this, "All details are ok", Toast.LENGTH_SHORT).show();



/*RequestQueue requestQueue= Volley.newRequestQueue(this);
            final String url="";
            JSONObject postParams=new JSONObject();
            try {
                postParams.put("Item_Identifier",Itemidstr);
                postParams.put("Item_Weight",ItemWeight);
                postParams.put("'Item_Fat_Content",FatContent);
                postParams.put("Item_Visibility",Itemvis);
                postParams.put("Item_Type",ItemType);
                postParams.put("Item_MRP",Itemmrp);
                postParams.put("Outlet_Identifier",OutletIdstr);
                postParams.put("Outlet_Establishment_Year",eyear);
                postParams.put("Outlet_Size",OutLetSize);
                postParams.put("Outlet_Location_Type",OutletLocation);
                postParams.put("Outlet_Type",OutletType);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, url, postParams, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

                }
            });
            requestQueue.add(jsonObjectRequest);*//*

        }

        /*else{
            Toast.makeText(this, "make sure all the details are given correctly", Toast.LENGTH_SHORT).show();
        }*/


        }
    }
}
