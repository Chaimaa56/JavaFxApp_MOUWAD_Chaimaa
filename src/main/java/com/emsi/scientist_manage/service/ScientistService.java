package com.emsi.scientist_manage.service;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.emsi.scientist_manage.dao.impl.DB;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.emsi.scientist_manage.dao.ScientistDao;
import com.emsi.scientist_manage.dao.impl.ScientistDaoImpl;
import com.emsi.scientist_manage.entities.Scientist;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ScientistService {
	private static ScientistDao scientistDao = new ScientistDaoImpl();

	public static List<Scientist> findAll() {
		return scientistDao.findAll();
	}

	public static void save(Scientist scientist) {
		scientistDao.insert(scientist);
	}

	public static void update(Scientist scientist) {
		scientistDao.update(scientist);
	}

	public static void remove(Scientist scientist) {
		scientistDao.deleteById(scientist.getId());
	}

	//Txt file
	/*public static void readFromTxtToList(String inputFilePath, String outputFilePath) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
			ArrayList<Scientist> list = new ArrayList<Scientist>();
			Scientist s = null;
			String readLine = br.readLine();
			while (readLine != null) {
				String[] scientist = readLine.split("\\|");
				s = new Scientist();
				s.setFieldOfStudy(scientist[0].trim());
				s.setYearsOfExperience(Integer.parseInt(scientist[1].trim()));
				s.setResearchInterests(scientist[2].trim());
				s.setPublications(scientist[3].trim());
				s.setName(scientist[4].trim());
				s.setAwardsAndHonors(scientist[5].trim());
				list.add(s);
				readLine = br.readLine();
			}
			try (FileOutputStream fout = new FileOutputStream(outputFilePath)) {
				for (Scientist scien : list) {
					fout.write(scien.toString().getBytes());
					fout.write('\n');
					System.out.println("Scientist: " + scien.toString());
				}
			} catch (IOException e) {
				System.out.println(e.getStackTrace());
			}
		}
	}
*/
	public static void readFromTxtAndInsertToDatabase(String filePath) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			ArrayList<Scientist> list = new ArrayList<>();
			Scientist s;
			String readLine = br.readLine();
			while (readLine != null) {
				String[] scientist = readLine.split("\\|");
				s = new Scientist();
				s.setFieldOfStudy(scientist[0].trim());
				s.setYearsOfExperience(Integer.parseInt(scientist[1].trim()));
				s.setResearchInterests(scientist[2].trim());
				s.setPublications(Integer.parseInt(scientist[3].trim()));
				s.setName(scientist[4].trim());
				s.setAwardsAndHonors(Integer.parseInt(scientist[5].trim()));
				scientistDao.insert(s);
				readLine = br.readLine();
			}

		}
	}
		//Excel file
	/*public void readFromExcelFileToAnother() throws IOException {
		try (FileInputStream fis = new FileInputStream("src/main/resources/scientistInputDataExcel.xlsx")) {
			Workbook workbook = new XSSFWorkbook(fis);

			Sheet sheet = workbook.getSheetAt(0);

			List<String[]> dataList = new ArrayList<>();

			Iterator<Row> rowIterator = sheet.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				String[] data = new String[6];
				for (int i = 0; i < 6; i++) {
					data[i] = row.getCell(i).toString();
				}
				dataList.add(data);
			}

			for (String[] data : dataList) {
				System.out.println("Name: " + data[0] + ", FieldOfStudy: " + data[1] + ", YearsOfInterests: " + data[2]
						+ ", researchInterests: " + data[3] + ", publications: " + data[4] + ", awardsAndHonors: "
						+ data[5]);
			}

			// Create a new Excel workbook
			Workbook newWorkbook = new XSSFWorkbook();
			Sheet newSheet = newWorkbook.createSheet("Sheet1");

			// Write the List data into the new Excel sheet
			int rowIndex = 0;
			for (String[] data : dataList) {
				Row row = newSheet.createRow(rowIndex++);
				for (int i = 0; i < 6; i++) {
					Cell cell = row.createCell(i);
					cell.setCellValue(data[i]);
				}
			}

		}
	}
*/
		public static void readFromExcelFileToDB(String filePath) throws FileNotFoundException {
			try (FileInputStream fis = new FileInputStream(filePath)) {
				Workbook workbook = new XSSFWorkbook(fis);

				Sheet sheet = workbook.getSheetAt(0);

				List<String[]> dataList = new ArrayList<>();

				Iterator<Row> rowIterator = sheet.iterator();

				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					String name = row.getCell(0).getStringCellValue();
					String field = row.getCell(1).getStringCellValue();
					int years = (int) row.getCell(2).getNumericCellValue();
					String research = row.getCell(3).getStringCellValue();
					int pub = (int)row.getCell(4).getNumericCellValue();
					int awards = (int)row.getCell(5).getNumericCellValue();

					Scientist scientist = new Scientist(null, name, field, years, research, pub, awards);
					scientistDao.insert(scientist);

				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
			//Json file
/*
	public static void readFromJsonFileToAnother() throws Exception {
		List<Scientist> scientistList = new ArrayList<>();

		BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/scientistInputDataJson.txt"));
		String json = "";
		String line;
		while ((line = reader.readLine()) != null) {
			json += line;
		}
		reader.close();

		Gson gson = new Gson();
		scientistList = gson.fromJson(json, new TypeToken<List<Scientist>>() {}.getType());

		for (Scientist sc : scientistList) {
			System.out.println("Scientist: " + sc + "\n");
		}

		try (FileOutputStream fout = new FileOutputStream("src/main/resources/scientistOutputDataJson.txt")) {
			Gson gson1 = new Gson();
			String json1 = gson1.toJson(scientistList).toString();
			fout.write(json1.getBytes());
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}

}*/
			public static void readFromJsonFileToDB(String filePath) throws Exception {
				List<Scientist> scientistList = new ArrayList<>();

				BufferedReader reader = new BufferedReader(new FileReader(filePath));
				StringBuilder json = new StringBuilder();
				String line;
				while ((line = reader.readLine()) != null) {
					json.append(line);
				}
				reader.close();

				Gson gson = new Gson();
				scientistList = gson.fromJson(json.toString(), new TypeToken<List<Scientist>>() {}.getType());

				for (Scientist scientist : scientistList) {
					// Save the scientist object to the database
					scientistDao.insert(scientist);
				}
			}
	public static void exportToTxt(String filePath) {
		List<Scientist> scientists = scientistDao.findAll();

		try (FileWriter writer = new FileWriter(filePath)) {
			for (Scientist scientist : scientists) {
				String line = scientist.getFieldOfStudy() + " | " + scientist.getYearsOfExperience()
						+ " | " + scientist.getResearchInterests() + " | " + scientist.getPublications()
						+ " | " + scientist.getName() + " | " + scientist.getAwardsAndHonors() + "\n";
				writer.write(line);
			}
			System.out.println("Data exported to the text file successfully.");
		} catch (IOException e) {
			System.out.println("An error occurred while exporting data: " + e.getMessage());
		}
	}
	public static void exportToExcel(String filePath) {
		List<Scientist> scientists = scientistDao.findAll();

		try (Workbook workbook = new XSSFWorkbook()) {
			Sheet sheet = workbook.createSheet("Scientists");

			// Create header row
			Row headerRow = sheet.createRow(0);
			headerRow.createCell(0).setCellValue("Field of Study");
			headerRow.createCell(1).setCellValue("Years of Experience");
			headerRow.createCell(2).setCellValue("Research Interests");
			headerRow.createCell(3).setCellValue("Publications");
			headerRow.createCell(4).setCellValue("Name");
			headerRow.createCell(5).setCellValue("Awards and Honors");

			// Populate data rows
			int rowIdx = 1;
			for (Scientist scientist : scientists) {
				Row dataRow = sheet.createRow(rowIdx++);
				dataRow.createCell(0).setCellValue(scientist.getFieldOfStudy());
				dataRow.createCell(1).setCellValue(scientist.getYearsOfExperience());
				dataRow.createCell(2).setCellValue(scientist.getResearchInterests());
				dataRow.createCell(3).setCellValue(scientist.getPublications());
				dataRow.createCell(4).setCellValue(scientist.getName());
				dataRow.createCell(5).setCellValue(scientist.getAwardsAndHonors());
			}

			// Write the workbook to the file
			try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
				workbook.write(outputStream);
				System.out.println("Data exported to the Excel file successfully.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred while exporting data: " + e.getMessage());
		}
	}
	public static void exportToJson(String filePath) {
		List<Scientist> scientists = scientistDao.findAll();

		try (FileWriter writer = new FileWriter(filePath)) {
			Gson gson = new Gson();
			gson.toJson(scientists, writer);
			System.out.println("Data exported to the JSON file successfully.");
		} catch (IOException e) {
			System.out.println("An error occurred while exporting data: " + e.getMessage());
		}
	}

}
