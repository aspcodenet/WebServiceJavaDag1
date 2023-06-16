package se.systementor.webservicejavadag1.services.Smhi.Data;

import java.util.ArrayList;
import java.util.Date;

/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */
public class Geometry{
    public String type;
    public ArrayList<ArrayList<Double>> coordinates;
}

