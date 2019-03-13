package controllers;

public abstract class Area {

	public Area(String name) {
    	this.name = name;
    	this.id = AreaCatalogue.areaIDs.size() + 1;
    	AreaCatalogue.areaIDs.put(id, this);
    	AreaCatalogue.areaNames.put(name, this);
	}

    protected String name;
    protected Integer id;
    
    public String getName() {
        return name;
    }

    public Integer getID() {
        return id;
    }
    
    public abstract Integer getPopulation();
}