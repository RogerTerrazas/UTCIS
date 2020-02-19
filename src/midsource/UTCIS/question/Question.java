//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package midsource.UTCIS.question;

import java.util.List;
import org.openqa.selenium.WebElement;

public class Question {
    public String question;
    public String strdis;
    public String strdisPercentage;

    public String disagree;
    public String disagreePercentage;

    public String neutral;
    public String neutralPercentage;

    public String agree;
    public String agreePercentage;

    public String stragr;
    public String stragrPercentage;

    public String numresp;
    public String normavg;
    public String orgavg;
    public String colavg;
    public String univavg;

    public Question(List<WebElement> elements) {
        this.question = ((WebElement) elements.get(0)).getText();

        this.strdis = returnWholeValue(((WebElement) elements.get(1)).getText());
        this.strdisPercentage = returnPercentage(((WebElement) elements.get(1)).getText());


        this.disagree = returnWholeValue(((WebElement) elements.get(2)).getText());
        this.disagreePercentage = returnPercentage(((WebElement) elements.get(2)).getText());

        this.neutral = returnWholeValue(((WebElement) elements.get(3)).getText());
        this.neutralPercentage = returnPercentage(((WebElement) elements.get(3)).getText());

        this.agree = returnWholeValue(((WebElement) elements.get(4)).getText());
        this.agreePercentage = returnPercentage(((WebElement) elements.get(4)).getText());

        this.neutral = returnWholeValue(((WebElement) elements.get(3)).getText());
        this.neutralPercentage = returnPercentage(((WebElement) elements.get(3)).getText());

        this.stragr = returnWholeValue(((WebElement) elements.get(5)).getText());
        this.stragrPercentage = returnPercentage(((WebElement) elements.get(5)).getText());

        this.numresp = ((WebElement) elements.get(6)).getText();
        this.normavg = ((WebElement) elements.get(7)).getText();
        this.orgavg = ((WebElement) elements.get(8)).getText();
        this.colavg = ((WebElement) elements.get(9)).getText();
        this.univavg = ((WebElement) elements.get(10)).getText();
    }

    public String returnPercentage(String raw) {
        int i = 0;
        while (raw.charAt(i) != ' ') {
            i++;
        }
        return raw.substring(i+2, raw.length()-2);
    }

    public String returnWholeValue(String raw) {
        int i = 0;
        while (raw.charAt(i) != ' ') {
            i++;
        }
        return raw.substring(0, i);
    }
}
