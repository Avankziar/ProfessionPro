package main.java.de.avankziar.professionpro.interfaces;

import java.util.ArrayList;

import org.bukkit.Material;

import main.java.de.avankziar.professionpro.enums.Behavior;
import main.java.de.avankziar.professionpro.enums.ExpPerLevelMode;

public class Profession 
{
	private String name;
	private int maxlevel;
	private boolean cangetexp;
	private ExpPerLevelMode expperlevelmode;
	private double expperlevel;
	private double endvalue;
	private double curvevalue;
	private Behavior behavior;
	private double expmulti;
	private double moneymulti;
	private double moneyendmulti;
	private ArrayList<ActivityList> activitylist;
	private boolean caninteractwithblocks;
	private ArrayList<Material> professionblocks;
	
	public Profession(String name, int maxlevel, boolean cangetexp, ExpPerLevelMode expperlevelmode, double expperlevel,
			double endvalue, double curvevalue, Behavior behavior, double expmulti, double moneymulti, double moneyendmulti,
			ArrayList<ActivityList> activitylist, boolean caninteractwithblocks, ArrayList<Material> professionblocks)
	{
		setName(name);
		setMaxlevel(maxlevel);
		setCangetexp(cangetexp);
		setExpperlevelmode(expperlevelmode);
		setExpperlevel(expperlevel);
		setEndvalue(endvalue);
		setCurvevalue(curvevalue);
		setBehavior(behavior);
		setExpmulti(expmulti);
		setMoneymulti(moneymulti);
		setMoneyendmulti(moneyendmulti);
		setActivitylist(activitylist);
		setCaninteractwithblocks(caninteractwithblocks);
		setProfessionblocks(professionblocks);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getMaxlevel() {
		return maxlevel;
	}

	public void setMaxlevel(int maxlevel) {
		this.maxlevel = maxlevel;
	}

	public boolean isCangetexp() {
		return cangetexp;
	}

	public void setCangetexp(boolean cangetexp) {
		this.cangetexp = cangetexp;
	}

	public ExpPerLevelMode getExpperlevelmode() {
		return expperlevelmode;
	}

	public void setExpperlevelmode(ExpPerLevelMode expperlevelmode) {
		this.expperlevelmode = expperlevelmode;
	}

	public double getExpperlevel() {
		return expperlevel;
	}

	public void setExpperlevel(double expperlevel) {
		this.expperlevel = expperlevel;
	}

	public double getEndvalue() {
		return endvalue;
	}

	public void setEndvalue(double endvalue) {
		this.endvalue = endvalue;
	}

	public double getCurvevalue() {
		return curvevalue;
	}

	public void setCurvevalue(double curvevalue) {
		this.curvevalue = curvevalue;
	}

	public Behavior getBehavior() {
		return behavior;
	}

	public void setBehavior(Behavior behavior) {
		this.behavior = behavior;
	}

	public double getExpmulti() {
		return expmulti;
	}

	public void setExpmulti(double expmulti) {
		this.expmulti = expmulti;
	}

	public double getMoneymulti() {
		return moneymulti;
	}

	public void setMoneymulti(double moneymulti) {
		this.moneymulti = moneymulti;
	}

	public double getMoneyendmulti() {
		return moneyendmulti;
	}

	public void setMoneyendmulti(double moneyendmulti) {
		this.moneyendmulti = moneyendmulti;
	}

	public ArrayList<ActivityList> getActivitylist() {
		return activitylist;
	}

	public void setActivitylist(ArrayList<ActivityList> activitylist) {
		this.activitylist = activitylist;
	}

	public boolean isCaninteractwithblocks() {
		return caninteractwithblocks;
	}

	public void setCaninteractwithblocks(boolean caninteractwithblocks) {
		this.caninteractwithblocks = caninteractwithblocks;
	}

	public ArrayList<Material> getProfessionblocks() {
		return professionblocks;
	}

	public void setProfessionblocks(ArrayList<Material> professionblocks) {
		this.professionblocks = professionblocks;
	}
}
