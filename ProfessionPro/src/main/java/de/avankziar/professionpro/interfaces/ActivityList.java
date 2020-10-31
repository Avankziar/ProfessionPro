package main.java.de.avankziar.professionpro.interfaces;

import java.util.ArrayList;

import main.java.de.avankziar.professionpro.enums.Activity;

public class ActivityList 
{
	private Activity activity;
	private ArrayList<MaterialList> materiallist;
	
	public ActivityList(Activity activity, ArrayList<MaterialList> materiallist)
	{
		setActivity(activity);
		setMateriallist(materiallist);
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public ArrayList<MaterialList> getMateriallist() {
		return materiallist;
	}

	public void setMateriallist(ArrayList<MaterialList> materiallist) {
		this.materiallist = materiallist;
	}
}
