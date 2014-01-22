package com.dpzf.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.avaje.ebean.Ebean;
import com.dpzf.model.Area;

public class Area_JSON {

	public static Logger log = Logger.getLogger(Area_JSON.class);
	public static final String PATH = "src/main/webapp/area/";

	public void generateProvince() {

		List<Area> provinces = Ebean.find(Area.class).where().eq("level", "1").findList();
		JSONObject jsonObj = generateJSON("provinces", provinces);
		try {
			FileUtils.writeStringToFile(new File(PATH + "province.json"), jsonObj.toString(), "UTF-8");
		} catch (IOException e) {
			log.error("Area_JSON generate province failure.");
			e.printStackTrace();
		}
		log.info("province ==> " + jsonObj.toString());

	}

	public void generateCity() {
		List<Area> provinces = Ebean.find(Area.class).where().eq("level", "1").findList();
		for (Area province : provinces) {
			List<Area> cities = Ebean.find(Area.class).where().like("code", province.getCode() + "%").eq("level", "2").findList();
			JSONObject jsonObj = generateJSON("cities", cities);
			try {
				FileUtils.writeStringToFile(new File(PATH + province.getCode() + ".json"), jsonObj.toString(), "UTF-8");
			} catch (IOException e) {
				log.error("Area_JSON generate province failure.");
				e.printStackTrace();
			}
			log.info("city ==> " + jsonObj.toString());
		}

	}

	public void generateCounty() {
		List<Area> provinces = Ebean.find(Area.class).where().eq("level", "1").findList();
		for (Area province : provinces) {
			List<Area> cities = Ebean.find(Area.class).where().like("code", province.getCode() + "%").eq("level", "2").findList();
			for (Area city : cities) {
				List<Area> counties = Ebean.find(Area.class).where().like("code", city.getCode().substring(0, 4) + "%").eq("level", "3").findList();
				JSONObject jsonObj = generateJSON("counties", counties);
				try {
					FileUtils.writeStringToFile(new File(PATH + city.getCode() + ".json"), jsonObj.toString(), "UTF-8");
				} catch (IOException e) {
					log.error("Area_JSON generate province failure.");
					e.printStackTrace();
				}
				log.info("city ==> " + jsonObj.toString());
			}
		}
	}

	public void generateTown() {
		List<Area> provinces = Ebean.find(Area.class).where().eq("level", "1").findList();
		for (Area province : provinces) {
			List<Area> cities = Ebean.find(Area.class).where().like("code", province.getCode() + "%").eq("level", "2").findList();
			for (Area city : cities) {
				List<Area> counties = Ebean.find(Area.class).where().like("code", city.getCode().substring(0, 4) + "%").eq("level", "3").findList();
				for (Area county : counties) {
					List<Area> towns = Ebean.find(Area.class).where().like("code", county.getCode().substring(0, 6) + "%").eq("level", "4")
							.findList();
					JSONObject jsonObj = generateJSON("towns", towns);
					try {
						FileUtils.writeStringToFile(new File(PATH + county.getCode() + ".json"), jsonObj.toString(), "UTF-8");
					} catch (IOException e) {
						log.error("Area_JSON generate province failure.");
						e.printStackTrace();
					}
					log.info("city ==> " + jsonObj.toString());
				}
			}
		}
	}

	public void generateVillage() {
		List<Area> provinces = Ebean.find(Area.class).where().eq("level", "1").findList();
		for (Area province : provinces) {
			List<Area> cities = Ebean.find(Area.class).where().like("code", province.getCode() + "%").eq("level", "2").findList();
			for (Area city : cities) {
				List<Area> counties = Ebean.find(Area.class).where().like("code", city.getCode().substring(0, 4) + "%").eq("level", "3").findList();
				for (Area county : counties) {
					List<Area> towns = Ebean.find(Area.class).where().like("code", county.getCode().substring(0, 6) + "%").eq("level", "4")
							.findList();
					for (Area town : towns) {
						List<Area> villages = Ebean.find(Area.class).where().like("code", town.getCode().substring(0, 9) + "%").eq("level", "5")
								.findList();
						JSONObject jsonObj = generateJSON("villages", villages);
						try {
							FileUtils.writeStringToFile(new File(PATH + town.getCode() + ".json"), jsonObj.toString(), "UTF-8");
						} catch (IOException e) {
							log.error("Area_JSON generate province failure.");
							e.printStackTrace();
						}
						log.info("city ==> " + jsonObj.toString());
					}
				}
			}
		}
	}

	private JSONObject generateJSON(String json_name, List<Area> areas) {
		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();

		for (Area area : areas) {
			JSONObject json = new JSONObject();
			json.put("level", area.getLevel());
			json.put("name", area.getName());
			json.put("code", area.getCode());
			jsonArr.put(json);
		}
		jsonObj.put(json_name, jsonArr);
		return jsonObj;
	}

	public static void main(String[] args) {
		new Area_JSON().generateVillage();
	}
}
