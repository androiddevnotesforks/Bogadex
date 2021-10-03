package fr.boitakub.bgg.client;

import com.tickaroo.tikxml.annotation.Attribute;
import com.tickaroo.tikxml.annotation.Element;
import com.tickaroo.tikxml.annotation.Path;
import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

import java.util.List;

@Xml
public class BoardGame extends Publishable {

    @PropertyElement
    public String thumbnail;

    @PropertyElement
    public String image;

    @PropertyElement
    public String description;

    @Path("minplayers")
    @Attribute(name = "value")
    public int minplayers;

    @Path("maxplayers")
    @Attribute(name = "value")
    public int maxplayers;

    @Path("playingtime")
    @Attribute(name = "value")
    public int playingtime;

    @Path("minplaytime")
    @Attribute(name = "value")
    public int minplaytime;

    @Path("maxplaytime")
    @Attribute(name = "value")
    public int maxplaytime;

    @Path("minage")
    @Attribute(name = "value")
    int minage;

    @Element(name = "poll")
    List<Poll> polls;

    @Element(name = "link")
    List<Link> links;

    @Element
    public Statistics statistics;

    @Path("versions")
    @Element
    List<Version> versions;

}