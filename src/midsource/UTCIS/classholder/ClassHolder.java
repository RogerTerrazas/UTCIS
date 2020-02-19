//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package midsource.UTCIS.classholder;

import java.util.ArrayList;
import java.util.List;
import midsource.UTCIS.question.Question;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ClassHolder {
    public String instructor;
    public String course;
    public String link;
    public String department;
    public String classNumber;
    public String unique;
    public String semester;
    public String year;
    public String semyear;
    public String responses;
    public String studentsEnrolled;
    public List<Question> questions;

    public ClassHolder(String[] information, List<WebElement> elements) {
        super();
        label19: {
            //super(); <---- from old code? May cause an error since I switched it to above ^
            this.instructor = ((WebElement)elements.get(0)).getText();
            this.link = ((WebElement)elements.get(0)).findElement(By.tagName("a")).getAttribute("href");
            this.department = information[0];
            this.classNumber = information[1];
            this.course = ((WebElement)elements.get(1)).getText();
            this.unique = ((WebElement)elements.get(2)).getText();
            String[] tmp = ((WebElement)elements.get(3)).getText().split(" ");
            this.semester = tmp[0];
            this.year = tmp[1];
            String var4;
            switch((var4 = this.semester).hashCode()) {
                case -1811812819:
                    if (var4.equals("Spring")) {
                        this.semyear = this.year + "2";
                        break label19;
                    }
                    break;
                case 2182043:
                    if (var4.equals("Fall")) {
                        this.semyear = this.year + "9";
                        break label19;
                    }
            }

            this.semyear = "Invalid";
        }

        this.questions = new ArrayList();
    }

    public void addQuestion(Question q) {
        this.questions.add(q);
    }

    public String getURL() {
        return this.link;
    }
}
