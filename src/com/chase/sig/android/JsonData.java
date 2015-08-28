package com.chase.sig.android;

/**
 * 
 * POJO JAVA file JOSN DATA
 *
 */
public class JsonData
{
    private String[] errors;

    private Locations[] locations;

    public String[] getErrors ()
    {
        return errors;
    }

    public void setErrors (String[] errors)
    {
        this.errors = errors;
    }

    public Locations[] getLocations ()
    {
        return locations;
    }

    public void setLocations (Locations[] locations)
    {
        this.locations = locations;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [errors = "+errors+", locations = "+locations+"]";
    }
}
