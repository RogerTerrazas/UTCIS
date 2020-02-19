//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package midsource.UTCIS.main;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import midsource.UTCIS.classholder.ClassHolder;
import midsource.UTCIS.question.Question;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LaitsStatsManager {
    private static final String SEARCHLINK = "https://utdirect.utexas.edu/ctl/ecis/results/search.WBX";
    private static final int QMULT = 5;
    static WebDriver driver;
    Path currentDirectory = Paths.get(".");
    static String userPreferences = "config.properties";
    static String driverLocation;
    static String driverType;
    static String listingsFile;
    static String saveFile;
    static Scanner scanner;
    static HashMap<String, Integer> questionMap;
    static JButton prefsSubmit;
    static JButton browse;
    static JPanel prefPanel;
    static JPanel subPrefs;
    static JPanel radioPrefs;
    static JPanel browsePrefs;
    static JRadioButton chromeRadio;
    static JRadioButton geckoRadio;
    static ButtonGroup radioGroup;
    static JFrame prefFrame;
    static JLabel prefsLabel;
    static JLabel prefsBrowseLabel;
    static boolean prefsComplete;
    private static JFrame mainFrame;
    static JPanel mainPanel;
    static JPanel chooserPanel;
    static JPanel infoPanel;
    static JPanel loginPanel;
    static JPanel savePanel;
    static JPanel finalPanel;
    static JMenuBar menuBar;
    static JMenu edit;
    static JMenuItem userPrefs;
    static JLabel chooserLabel;
    static JLabel loginLabel;
    static JButton chooserButton;
    static JButton mainSubmit;
    static JButton saveBrowse;
    static JTextField username;
    static JTextField saveLocation;
    static JPasswordField password;
    static boolean cycled;

    static {
        scanner = new Scanner(System.in);
        questionMap = new HashMap();
        prefsComplete = false;
        cycled = false;
    }

    public LaitsStatsManager() {
    }

    public static void main(String[] args) {
        getProperties();
        mainFrame = new JFrame("LAITS Stats");
        mainFrame.setDefaultCloseOperation(3);
        menuBar = new JMenuBar();
        edit = new JMenu("Edit");
        edit.setMnemonic(69);
        userPrefs = new JMenuItem("User Preferences");
        userPrefs.setMnemonic(80);
        userPrefs.setActionCommand("editPrefs");
        userPrefs.addActionListener(new LaitsStatsManager.MenuListener());
        edit.add(userPrefs);
        menuBar.add(edit);
        mainFrame.setJMenuBar(menuBar);
        mainPanel = new JPanel(new GridLayout(3, 1));
        chooserPanel = new JPanel(new GridLayout(2, 1));
        chooserLabel = new JLabel("Please select file");
        chooserPanel.add(chooserLabel);
        chooserButton = new JButton("Browse");
        chooserButton.setVerticalTextPosition(0);
        chooserButton.setHorizontalTextPosition(10);
        chooserButton.setMnemonic(66);
        chooserButton.setActionCommand("browse");
        chooserButton.addActionListener(new LaitsStatsManager.MainListener());
        chooserPanel.add(chooserButton);
        mainPanel.add(chooserPanel);
        loginPanel = new JPanel(new GridLayout(1, 2));
        infoPanel = new JPanel(new GridLayout(2, 1));
        loginLabel = new JLabel("Login Information");
        loginPanel.add(loginLabel);
        username = new JTextField("username");
        password = new JPasswordField("password");
        infoPanel.add(username);
        infoPanel.add(password);
        loginPanel.add(infoPanel);
        mainPanel.add(loginPanel);
        finalPanel = new JPanel(new GridLayout(1, 2));
        savePanel = new JPanel(new GridLayout(2, 1));
        saveLocation = new JTextField("Name of saved file");
        savePanel.add(saveLocation);
        saveBrowse = new JButton("Save As");
        saveBrowse.setVerticalTextPosition(0);
        saveBrowse.setHorizontalTextPosition(10);
        saveBrowse.setMnemonic(83);
        saveBrowse.setActionCommand("save");
        saveBrowse.addActionListener(new LaitsStatsManager.MainListener());
        savePanel.add(saveBrowse);
        finalPanel.add(savePanel);
        mainSubmit = new JButton("Start");
        mainSubmit.setVerticalTextPosition(0);
        mainSubmit.setHorizontalTextPosition(10);
        mainSubmit.setMnemonic(84);
        mainSubmit.setActionCommand("start");
        mainSubmit.addActionListener(new LaitsStatsManager.MainListener());
        finalPanel.add(mainSubmit);
        mainPanel.add(finalPanel);
        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
        questionMap.put("Overall, this course was", 0);
        questionMap.put("Overall, this instructor was", 1);
        questionMap.put("In my opinion, the workload in this course was", 2);
        questionMap.put("In my opinion, the workload in this course was:", 2);

        try {
            while(true) {
                while(!cycled) {
                    Thread.sleep(100L);
                }

                cycled = false;
                listingsFile = null;
                saveFile = null;
                username.setText("username");
                password.setText("password");
                saveLocation.setText("Name of saved file");
                chooserLabel.setText("Please select file");
            }
        } catch (Exception var2) {
            var2.printStackTrace();
            System.err.println("Encountered a fatal error. Exiting...");
        }
    }

    public static void runProcessor() throws Exception {
        String var0;
        List<String> columnLabels = new ArrayList<>(Arrays.asList(  "n_very_unsatisfactory_course",    "pct_very_unsatisfactory_course",    "n_unsatisfactory_course",    "pct_unsatisfactory_course",    "n_satisfactory_course",    "pct_satisfactory_course",    "n_very_good_course",    "pct_very_good_course",    "n_excellent_course",    "pct_excellent_course",    "n_course_rating",    "avg_course_rating",    "dept_avg_course_rating",    "college_avg_course_rating",    "ut_avg_course_rating",
                "n_very_unsatisfactory_instructor","pct_very_unsatisfactory_instructor","n_unsatisfactory_instructor","pct_unsatisfactory_instructor","n_satisfactory_instructor","pct_satisfactory_instructor","n_very_good_instructor","pct_very_good_instructor","n_excellent_instructor","pct_excellent_instructor","n_instructor_rating","avg_instructor_rating","dept_avg_instructor_rating","college_avg_instructor_rating","ut_avg_instructor_rating",
                "n_very_unsatisfactory_workload",  "pct_very_unsatisfactory_workload",  "n_unsatisfactory_workload",  "pct_unsatisfactory_workload",  "n_satisfactory_workload",  "pct_satisfactory_workload",  "n_very_good_workload",  "pct_very_good_workload",  "n_excellent_workload",  "pct_excellent_workload",  "n_workload_rating",  "avg_workload_rating",  "dept_avg_workload_rating",  "college_avg_workload_rating",  "ut_avg_workload_rating"
        ));

        switch((var0 = driverType).hashCode()) {
            case -1361128838:
                if (var0.equals("chrome")) {
                    System.setProperty("webdriver.chrome.driver", driverLocation);
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments(new String[]{"--headless"});
                    driver = new ChromeDriver(options);
                }
                break;
            case 98230121:
                if (var0.equals("gecko")) {
                    System.setProperty("webdriver.gecko.driver", driverLocation);
                    driver = new FirefoxDriver();
                }
        }

        BufferedReader br = new BufferedReader(new FileReader(new File(listingsFile)));


        boolean byCourse;
        byCourse = br.readLine().toLowerCase().equals("courses");

        driver.get("https://utdirect.utexas.edu/ctl/ecis/results/search.WBX");


        (new WebDriverWait(driver, 3L)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.id("IDToken1")) != null ? true : false;
            }
        });
        WebElement user = driver.findElement(By.id("IDToken1"));
        user.sendKeys(new CharSequence[]{username.getText()});
        WebElement passwd = driver.findElement(By.id("IDToken2"));
        passwd.sendKeys(new CharSequence[]{new String(password.getPassword())});
        WebElement submit = driver.findElement(By.name("Login.Submit"));
        submit.click();

        List<ClassHolder> classes = new ArrayList();

        WebElement question = null;
        for(String line = br.readLine(); line != null; line = br.readLine()) {

            if(byCourse){
                String[] s = line.split("-");

               while(s[0].length() < 3){ // for one char courses like "M", the drop down selection value is "M  "
                    s[0] += " ";
                }

                searchByCourse(classes, question, s);
            }else {
                String s = line.toLowerCase();
                searchByProfessor(classes, question, s);
            }


        }

        Iterator classIterator = classes.iterator();

        Iterator rowsIterator;

        int classesLeft = 0;
        while(classIterator.hasNext()) {
            classesLeft++;
            System.out.println("Classes remaining: " + (classes.size() - classesLeft) );
            ClassHolder ch = (ClassHolder)classIterator.next();
            driver.get(ch.getURL());
            (new WebDriverWait(driver, 3L)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    return d.findElement(By.tagName("tr")) != null ? true : false;
                }
            });


            //Find the number of students enrolled and the number of students that submitted the survey
            // -----------------------------------------------------------------------------
            List<WebElement> fieldSetEntries = driver.findElements(By.tagName("fieldset")).get(0).findElements(By.tagName("div"));

            String enrolledString =((WebElement)fieldSetEntries.get(5)).getText().toString(); //90 - String length (put may change if webpage differs
            String responsesString =((WebElement)fieldSetEntries.get(6)).getText().toString(); //36 - String length (put may change if webpage differs
            responsesString = responsesString.split("\\r?\\n")[0];

            int responseStringIndex = responsesString.length();
            while(Character.isDigit(responsesString.charAt(responseStringIndex-1))){ //grab the last int at the end of response string
                responseStringIndex--;
            }

            ch.studentsEnrolled = enrolledString.substring(88, enrolledString.length());
            ch.responses = responsesString.substring(responseStringIndex, responsesString.length());


            // Get data from table entries (variable "map" contains what questions to include in spreadsheet.
            List<WebElement> rows = driver.findElements(By.tagName("tr"));
            rowsIterator = rows.iterator();
            while(rowsIterator.hasNext()) {
                question = (WebElement)rowsIterator.next();
                List<WebElement> dataPoints = question.findElements(By.tagName("td"));
                if (dataPoints != null && dataPoints.size() != 0 && questionMap.get(((WebElement)dataPoints.get(0)).getText()) != null){ // && map.get(((WebElement)dataPoints.get(0)).getText()) != null) {
                    ch.addQuestion(new Question(dataPoints));
                }
            }
        }

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("ClassData");
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Course");
        row.createCell(1).setCellValue("CCYYS");
        row.createCell(2).setCellValue("Unique");
        row.createCell(3).setCellValue("Professor");
        row.createCell(4).setCellValue("Semester");
        row.createCell(5).setCellValue("Year");
        row.createCell(6).setCellValue("Department");
        row.createCell(7).setCellValue("Enrollment");
        row.createCell(8).setCellValue("Survey Forms Returned");

        rowsIterator = questionMap.keySet().iterator();

        while(rowsIterator.hasNext()) {
            String s = (String)rowsIterator.next();
            int i = 9 + (Integer) questionMap.get(s) * 15;
            int k = i - 9;
            row.createCell(i).setCellValue(columnLabels.get(k));
            row.createCell(i + 1).setCellValue(columnLabels.get(k + 1));

            row.createCell(i + 2).setCellValue(columnLabels.get(k + 2));
            row.createCell(i + 3).setCellValue(columnLabels.get(k + 3));

            row.createCell(i + 4).setCellValue(columnLabels.get(k + 4));
            row.createCell(i + 5).setCellValue(columnLabels.get(k + 5));

            row.createCell(i + 6).setCellValue(columnLabels.get(k + 6));
            row.createCell(i + 7).setCellValue(columnLabels.get(k + 7));

            row.createCell(i + 8).setCellValue(columnLabels.get(k + 8));
            row.createCell(i + 9).setCellValue(columnLabels.get(k + 9));

            row.createCell(i + 10).setCellValue(columnLabels.get(k + 10));
            row.createCell(i + 11).setCellValue(columnLabels.get(k + 11));
            row.createCell(i + 12).setCellValue(columnLabels.get(k + 12));
            row.createCell(i + 13).setCellValue(columnLabels.get(k + 13));
            row.createCell(i + 14).setCellValue(columnLabels.get(k + 14));
        }

        int rowIndex = 1;

        for(Iterator var30 = classes.iterator(); var30.hasNext(); ++rowIndex) {
            ClassHolder ch = (ClassHolder)var30.next();
            row = sheet.createRow((short)rowIndex);
            row.createCell(0).setCellValue(ch.course);
            row.createCell(1).setCellValue(ch.semyear);
            row.createCell(2).setCellValue(ch.unique);
            row.createCell(3).setCellValue(ch.instructor);
            row.createCell(4).setCellValue(ch.semester);
            row.createCell(5).setCellValue(ch.year);
            row.createCell(6).setCellValue(ch.department);
            row.createCell(7).setCellValue(ch.studentsEnrolled);
            row.createCell(8).setCellValue(ch.responses);

            Iterator chQuestionIterator = ch.questions.iterator();

            while(chQuestionIterator.hasNext()) {
                Question q = (Question)chQuestionIterator.next();
                int i = 9 + (Integer) questionMap.get(q.question) *15;
                row.createCell(i).setCellValue(q.strdis);
                row.createCell(i + 1).setCellValue(q.strdisPercentage);

                row.createCell(i + 2).setCellValue(q.disagree);
                row.createCell(i + 3).setCellValue(q.disagreePercentage);

                row.createCell(i + 4).setCellValue(q.neutral);
                row.createCell(i + 5).setCellValue(q.neutralPercentage);

                row.createCell(i + 6).setCellValue(q.agree);
                row.createCell(i + 7).setCellValue(q.agreePercentage);

                row.createCell(i + 8).setCellValue(q.stragr);
                row.createCell(i + 9).setCellValue(q.stragrPercentage);

                row.createCell(i + 10).setCellValue(q.numresp);

                row.createCell(i + 11).setCellValue(q.normavg);
                row.createCell(i + 12).setCellValue(q.orgavg);
                row.createCell(i + 13).setCellValue(q.colavg);
                row.createCell(i + 14).setCellValue(q.univavg);
            }
        }

        br.close();
        FileOutputStream fileOut = new FileOutputStream(saveFile);
        wb.write(fileOut);
        fileOut.close();
        driver.close();
        wb.close();
    }

    public static void searchByCourse(List<ClassHolder> classes, WebElement question, String[] s){
        (new WebDriverWait(driver, 3L)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.linkText("Search by Course")).isDisplayed();
            }
        });
        driver.findElement(By.linkText("Search by Course")).click();
        Select dropdown = new Select(driver.findElement(By.id("s_in_search_course_dept")));
        dropdown.selectByValue(s[0]);
        driver.findElement(By.id("s_in_search_course_num")).clear();
        driver.findElement(By.id("s_in_search_course_num")).sendKeys(new CharSequence[]{s[1]});
        driver.findElement(By.id("s_in_search_course_num")).submit();
        try {
            Thread.sleep(1000L);
        }catch (InterruptedException ignored){

        }

        boolean moreResults = true;
        while(moreResults) {
            question = driver.findElement(By.id("results-view"));
            WebElement table = question.findElement(By.tagName("table")).findElement(By.tagName("tbody"));
            Iterator var13 = table.findElements(By.tagName("tr")).iterator();

            while (var13.hasNext()) {
                WebElement row = (WebElement) var13.next();
                List<WebElement> data = row.findElements(By.tagName("td"));
                if (data != null && data.size() != 0) {
                    if (((WebElement) data.get(1)).getText().contains(s[1])) {
                        classes.add(new ClassHolder(s, data));
                    } else {
                        moreResults = false;
                    }
                }
            }
            driver.findElement(By.xpath("//input[@value='Next page']")).click();
        }
    }

    public static void searchByProfessor(List<ClassHolder> classes, WebElement question, String s){
        (new WebDriverWait(driver, 3L)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.findElement(By.linkText("Search by Instructor Name")).isDisplayed();
            }
        });
        driver.findElement(By.linkText("Search by Instructor Name")).click();
        //Select dropdown = new Select(driver.findElement(By.id("s_in_search_course_dept")));
        //dropdown.selectByValue(s[0]);
        driver.findElement(By.id("s_in_search_name")).clear();
        driver.findElement(By.id("s_in_search_name")).sendKeys(new CharSequence[]{s});
        driver.findElement(By.id("s_in_search_name")).submit();
        try {
            Thread.sleep(1000L);
        }catch (InterruptedException ignored){
        }

        boolean moreResults = true;
        while(moreResults) {
            question = driver.findElement(By.id("results-view"));
            WebElement table = question.findElement(By.tagName("table")).findElement(By.tagName("tbody"));
            Iterator var13 = table.findElements(By.tagName("tr")).iterator();

            while (var13.hasNext()) {
                WebElement row = (WebElement) var13.next();
                List<WebElement> data = row.findElements(By.tagName("td"));
                if (data != null && data.size() != 0) {
                    if (((WebElement) data.get(0)).getText().toLowerCase().contains(s)) {
                        String[] courseValue = {"" , ""};
                        String rawCourseName = data.get(1).getText();
                        if(rawCourseName.charAt(1) == ' ' && Character.isDigit(rawCourseName.charAt(2))){ // if course name is only represented by one character
                            courseValue[0] = data.get(1).getText().substring(0,1);
                            courseValue[1] = data.get(1).getText().substring(2);
                        }else {
                            courseValue[0] = data.get(1).getText().substring(0,3);
                            courseValue[1] = data.get(1).getText().substring(3);
                        }
                        classes.add(new ClassHolder(courseValue, data));
                        System.out.println(data.get(3).getText());
                    } else {
                        moreResults = false;
                    }
                }
            }
            driver.findElement(By.xpath("//input[@value='Next page']")).click();
        }
    }
    public static void getProperties() {
        try {
            Properties prop = new Properties();
            InputStream inputStream = new FileInputStream(userPreferences);
            prop.load(inputStream);
            driverType = prop.getProperty("driver_type");
            driverLocation = prop.getProperty("driver_location");
        } catch (IOException var3) {
            setPrefs();
        }

    }

    private static void setPrefs() {
        prefFrame = new JFrame("LAITS Stats Setup");
        prefFrame.setDefaultCloseOperation(3);
        prefFrame.setLocationRelativeTo((Component)null);
        prefPanel = new JPanel(new GridLayout(3, 1));
        subPrefs = new JPanel(new GridLayout(1, 2));
        radioPrefs = new JPanel(new GridLayout(2, 1));
        browsePrefs = new JPanel(new GridLayout(2, 1));
        prefsLabel = new JLabel("Please input your user preferences below:");
        prefPanel.add(prefsLabel);
        chromeRadio = new JRadioButton("Chrome Driver");
        chromeRadio.setMnemonic(67);
        if (driverType != null) {
            if (driverType.equals("chrome")) {
                chromeRadio.setSelected(true);
            } else {
                chromeRadio.setSelected(false);
            }
        }

        geckoRadio = new JRadioButton("Gecko Driver");
        geckoRadio.setMnemonic(71);
        if (driverType != null) {
            if (driverType.equals("gecko")) {
                geckoRadio.setSelected(true);
            } else {
                geckoRadio.setSelected(false);
            }
        }

        radioGroup = new ButtonGroup();
        radioGroup.add(chromeRadio);
        radioGroup.add(geckoRadio);
        radioPrefs.add(chromeRadio);
        radioPrefs.add(geckoRadio);
        subPrefs.add(radioPrefs);
        if (driverLocation == null) {
            prefsBrowseLabel = new JLabel("Please select a driver");
        } else {
            prefsBrowseLabel = new JLabel(driverLocation);
        }

        browsePrefs.add(prefsBrowseLabel);
        browse = new JButton("Browse For Driver");
        browse.setVerticalTextPosition(0);
        browse.setHorizontalTextPosition(10);
        browse.setMnemonic(66);
        browse.setActionCommand("browse");
        browse.addActionListener(new LaitsStatsManager.PrefsListener());
        browsePrefs.add(browse);
        subPrefs.add(browsePrefs);
        prefPanel.add(subPrefs);
        prefsSubmit = new JButton("Submit");
        prefsSubmit.setVerticalTextPosition(0);
        prefsSubmit.setHorizontalTextPosition(10);
        prefsSubmit.setMnemonic(85);
        prefsSubmit.setActionCommand("prefsSubmit");
        prefsSubmit.addActionListener(new LaitsStatsManager.PrefsListener());
        prefPanel.add(prefsSubmit);
        prefFrame.add(prefPanel);
        prefFrame.pack();
        prefFrame.setVisible(true);

        while(!prefsComplete) {
            try {
                Thread.sleep(300L);
            } catch (InterruptedException var1) {
                var1.printStackTrace();
            }
        }

        prefsComplete = false;
    }

    static class MainListener implements ActionListener {
        MainListener() {
        }

        public void actionPerformed(ActionEvent event) {
            JFileChooser fileChooser;
            FileNameExtensionFilter filter;
            String var4;
            switch((var4 = event.getActionCommand()).hashCode()) {
                case -1380604278:
                    if (var4.equals("browse")) {
                        fileChooser = new JFileChooser();
                        filter = new FileNameExtensionFilter("TXT Files", new String[]{"txt"});
                        fileChooser.setFileFilter(filter);
                        if (fileChooser.showOpenDialog((Component)null) != 1) {
                            LaitsStatsManager.listingsFile = fileChooser.getSelectedFile().getAbsolutePath();
                            LaitsStatsManager.chooserLabel.setText(LaitsStatsManager.listingsFile);
                            LaitsStatsManager.mainFrame.pack();
                        }
                    }
                    break;
                case 3522941:
                    if (var4.equals("save")) {
                        fileChooser = new JFileChooser();
                        filter = new FileNameExtensionFilter("XLSX Files", new String[]{"xlsx"});
                        fileChooser.setFileFilter(filter);
                        if (fileChooser.showSaveDialog((Component)null) != 1) {
                            LaitsStatsManager.saveFile = fileChooser.getSelectedFile().getAbsolutePath() + ".xlsx";
                            LaitsStatsManager.saveLocation.setText(LaitsStatsManager.saveFile);
                            LaitsStatsManager.mainFrame.pack();
                        }
                    }
                    break;
                case 109757538:
                    if (var4.equals("start")) {
                        if (LaitsStatsManager.listingsFile == null) {
                            JOptionPane.showMessageDialog(LaitsStatsManager.prefFrame, "Please choose a file to read from", "WARNING: Invalid Parameters", 0);
                        } else if (LaitsStatsManager.saveFile == null && LaitsStatsManager.saveLocation.getText().equals("Save As")) {
                            JOptionPane.showMessageDialog(LaitsStatsManager.prefFrame, "Please choose a file to write to", "WARNING: Invalid Parameters", 0);
                        } else if (!LaitsStatsManager.username.getText().equals("username") && !LaitsStatsManager.username.getText().equals("")) {
                            if (!(new String(LaitsStatsManager.password.getPassword())).equals("password") && !(new String(LaitsStatsManager.password.getPassword())).equals("")) {
                                if (LaitsStatsManager.saveFile == null) {
                                    LaitsStatsManager.saveFile = LaitsStatsManager.saveLocation.getText();
                                }

                                try {
                                    LaitsStatsManager.runProcessor();
                                    LaitsStatsManager.cycled = true;
                                } catch (Exception var6) {
                                    LaitsStatsManager.driver.quit();
                                    var6.printStackTrace();
                                    System.err.println("Encountered a fatal error while processing. Exiting...");
                                    JOptionPane.showMessageDialog((Component)null, "Encountered Fatal Error While Processing", "ERROR", 0);
                                }
                            } else {
                                JOptionPane.showMessageDialog(LaitsStatsManager.prefFrame, "Please enter your password", "WARNING: Invalid Parameters", 0);
                            }
                        } else {
                            JOptionPane.showMessageDialog(LaitsStatsManager.prefFrame, "Please choose a valid username", "WARNING: Invalid Parameters", 0);
                        }
                    }
            }

        }
    }

    static class MenuListener implements ActionListener {
        MenuListener() {
        }

        public void actionPerformed(ActionEvent event) {
            String var2;
            switch((var2 = event.getActionCommand()).hashCode()) {
                case -1879934266:
                    if (var2.equals("editPrefs")) {
                        LaitsStatsManager.mainFrame.setVisible(false);
                        LaitsStatsManager.setPrefs();
                        LaitsStatsManager.mainFrame.setVisible(true);
                    }
                default:
            }
        }
    }

    static class PrefsListener implements ActionListener {
        PrefsListener() {
        }

        public void actionPerformed(ActionEvent event) {
            String var2;
            switch((var2 = event.getActionCommand()).hashCode()) {
                case -1380604278:
                    if (var2.equals("browse")) {
                        JFileChooser driverChooser = new JFileChooser();
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXE Files", new String[]{"exe"});
                        driverChooser.setFileFilter(filter);
                        if (driverChooser.showOpenDialog((Component)null) != 1) {
                            LaitsStatsManager.driverLocation = driverChooser.getSelectedFile().getAbsolutePath();
                            LaitsStatsManager.prefsBrowseLabel.setText(LaitsStatsManager.driverLocation);
                            LaitsStatsManager.prefFrame.pack();
                        }
                    }
                    break;
                case 656935656:
                    if (var2.equals("prefsSubmit")) {
                        if (LaitsStatsManager.driverLocation != null && (LaitsStatsManager.chromeRadio.isSelected() || LaitsStatsManager.geckoRadio.isSelected())) {
                            Properties prop = new Properties();
                            if (LaitsStatsManager.chromeRadio.isSelected()) {
                                prop.setProperty("driver_type", "chrome");
                                LaitsStatsManager.driverType = "chrome";
                            } else if (LaitsStatsManager.geckoRadio.isSelected()) {
                                prop.setProperty("driver_type", "gecko");
                            }

                            prop.setProperty("driver_location", LaitsStatsManager.driverLocation);

                            try {
                                OutputStream output = new FileOutputStream(LaitsStatsManager.userPreferences);
                                prop.store(output, (String)null);
                                output.close();
                                LaitsStatsManager.prefFrame.setVisible(false);
                                LaitsStatsManager.prefFrame.dispose();
                                LaitsStatsManager.prefsComplete = true;
                            } catch (IOException var7) {
                                var7.printStackTrace();
                                System.err.println("Encountered a fatal error while writing user preferences. Exiting...");
                                JOptionPane.showMessageDialog((Component)null, "Encountered Fatal Error Writing Out User Prefs", "ERROR", 0);
                            }
                        } else if (LaitsStatsManager.driverLocation == null) {
                            JOptionPane.showMessageDialog(LaitsStatsManager.prefFrame, "Please enter a driver location", "WARNING: Invalid Parameters", 0);
                        } else if (!LaitsStatsManager.chromeRadio.isSelected() && !LaitsStatsManager.geckoRadio.isSelected()) {
                            JOptionPane.showMessageDialog(LaitsStatsManager.prefFrame, "Please choose a driver type", "WARNING: Invalid Parameters", 0);
                        }
                    }
            }

        }
    }
}
