package classes.slar;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.parser.client.SyntaxError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Main extends Application {
    public ArrayList<ArrayList<String>> matrix_a = new ArrayList<ArrayList<String>>();
    private Pane root;
    private Scene scene;
    ArrayList<String> buffer_a;
    Button btn_solve;
    Button import_excel;
    Button export_excel;
    Spinner<Integer> spinner;
    TextField[] txt;
    TextField[] txt1;
    File fileobject;
    String fnl_answ;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        int y = 0;
        int x = 0;
        int k = 0;

        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        scene = new Scene(root);

        Label count_unk = new Label("Кількість змінних та рядків");
        Label label_answer = new Label("Відповідь");
        Label answer = new Label();
        count_unk.setFont(new Font("Arial", 16));
        label_answer.setFont(new Font("Arial", 16));
        answer.setFont(new Font("Arial", 15));
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,10);
        spinner = new Spinner<>(valueFactory);
        spinner.getValueFactory().setValue(10);
        txt = new TextField[(int) Math.pow(spinner.getValue(),2)];
        txt1 = new TextField[spinner.getValue()];

        spinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                for (int i = 0; i < txt.length; i++) {
                    root.getChildren().remove(txt[i]);
                }
                for (int i = 0; i < txt1.length; i++) {
                    root.getChildren().remove(txt1[i]);
                }
                txt = new TextField[(int) Math.pow(spinner.getValue(),2)];
                txt1 = new TextField[spinner.getValue()];

                int y = 0;
                int x = 0;
                int k = 0;
                System.out.println((int) Math.pow(spinner.getValue(),2));
                for (int i = 1; i < Math.pow(spinner.getValue(),2)+1; i++) {
                    for (int j = 1; j < spinner.getValue()+1; j++) {
                        int count = spinner.getValue();
                        if (i < count+1 && i != 1){
                            x++;
                            System.out.println("one: " + i);
                            break;
                        }
                        else if (i < (count*j)+1 && i != 1) {
                            if (i == (count*(j-1)) + 1) {
                                root.getChildren().remove(txt1[k]);
                                txt1[k] = new TextField();
                                txt1[k].setLayoutX(90 +(x*70));
                                txt1[k].setLayoutY(80+(y*30));
                                txt1[k].setPrefWidth(50);
                                txt1[k].setText(""); //ab+k
                                txt1[k].setPromptText("b"+(y+1));
                                root.getChildren().add(txt1[k]);
                                k++;
                                x = 0;
                                y++;
                                System.out.println("ok " + i);

                                break;
                            } else {
                                x++;
                                root.getChildren().remove(txt1[k]);
                                txt1[k] = new TextField();
                                txt1[k].setLayoutX(90 +(x*70));
                                txt1[k].setLayoutY(80+(y*30));
                                txt1[k].setPrefWidth(50);
                                txt1[k].setText("");//ab+k
                                txt1[k].setPromptText("b"+(y+1));
                                root.getChildren().add(txt1[k]);
                                System.out.println("three: " + i);
                                break;
                            }
                        }
                    }
                    root.getChildren().remove(txt[i-1]);
                    txt[i-1] = new TextField();
                    txt[i-1].setLayoutX(25+(x*70));
                    txt[i-1].setLayoutY(80+(y*30));
                    txt[i-1].setPromptText("x"+(x+1));
                    txt[i - 1].setPrefWidth(50);
                    root.getChildren().add(txt[i-1]);
                }

            }
        });

        for (int i = 0; i < Math.pow(spinner.getValue(),2); i++) {
            root.getChildren().remove(txt[i]);
        }

        for (int i = 0; i < txt1.length; i++) {
            root.getChildren().remove(txt1[i]);
        }

        for (int i = 1; i < Math.pow(spinner.getValue(),2)+1; i++) {
            for (int j = 1; j < spinner.getValue() + 1; j++) {
                int count = spinner.getValue();
                if (i < count + 1 && i != 1) {
                    x++;
                    System.out.println("one: " + i);
                    break;
                } else if (i < (count * j) + 1 && i != 1) {
                    if (i == (count * (j - 1)) + 1) {
                        root.getChildren().remove(txt1[k]);
                        txt1[k] = new TextField();
                        txt1[k].setLayoutX(90 +(x*70));
                        txt1[k].setLayoutY(80+(y*30));
                        txt1[k].setPrefWidth(50);
                        txt1[k].setText("");//ab+k
                        txt1[k].setPromptText("b"+(y+1));
                        root.getChildren().add(txt1[k]);
                        k++;
                        x = 0;
                        y++;

                        break;
                    } else {
                        x++;
                        root.getChildren().remove(txt1[k]);
                        txt1[k] = new TextField();
                        txt1[k].setLayoutX(90 +(x*70));
                        txt1[k].setLayoutY(80+(y*30));
                        txt1[k].setPrefWidth(50);
                        txt1[k].setText(""); //ab+k
                        txt1[k].setPromptText("b"+(y+1));
                        root.getChildren().add(txt1[k]);
                        break;
                    }
                }
            }
            root.getChildren().remove(txt[i - 1]);
            txt[i - 1] = new TextField();txt[i - 1].setPrefWidth(50);
            txt[i - 1].setLayoutX(25 + (x * 70));
            txt[i - 1].setLayoutY(80 + (y * 30));
            txt[i - 1].setPromptText("x"+(x+1));
            root.getChildren().add(txt[i - 1]);
        }
        spinner.setLayoutX(25);
        spinner.setLayoutY(45);
        root.getChildren().add(spinner);
        btn_solve = new Button();
        btn_solve.setText("Розрахувати");
        btn_solve.setLayoutX(200);
        btn_solve.setLayoutY(45);
        buffer_a = new ArrayList<>();

        export_excel = new Button();
        import_excel = new Button();

        export_excel.setDisable(true);

        btn_solve.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String data_a;
                String data_b = null;
                String data_result;
                String[] fnl_result;
                fnl_answ = null;
                for (int i = 0; i < txt.length; i++) {

                    for (int j = 1; j < spinner.getValue() + 1; j++) {
                        if (i + 1 < spinner.getValue() + 1) {
                            buffer_a.add(txt[i].getText());
                            break;
                        } else if (i < (spinner.getValue() * j) && i != 0) {
                            if (i + 1 == (spinner.getValue() * (j - 1)) + 1) {
                                matrix_a.add(buffer_a);
                                buffer_a = new ArrayList<>();
                            }
                            buffer_a.add(txt[i].getText());
                            if (i+1 == txt.length) {
                                matrix_a.add(buffer_a);
                                buffer_a = new ArrayList<>();
                            }
                            break;
                        }
                    }
                }
                for (int i = 0; i < txt1.length; i++) {
                    if (i == 0) {
                        data_b = "{" + txt1[i].getText() + ",";
                    } else if (i + 1 == txt1.length) {
                        data_b = data_b + txt1[i].getText() + "}";
                    } else {
                        data_b = data_b + txt1[i].getText() + ",";
                    }
                }

                data_a = matrix_a.toString();


                System.out.println(data_a);
                matrix_a = new ArrayList<>();
                ExprEvaluator util = new ExprEvaluator(false, (short) 100);
                IExpr result;

                String solving = "LinearSolve(" + data_a.replace("[", "{").replace("]", "}") + "," + data_b + ")";
                result = util.eval(solving);
                System.out.println("Out[1]: " + result.toString());
                data_result = result.toString();

                fnl_result = data_result.replace("{", "").replace("}", "").split(",");
                for (int i = 0; i < fnl_result.length; i++){
                    if (i == 0){
                        fnl_answ = "x" + (i+1) +" = " + fnl_result[i] + " ";
                    } else {
                        fnl_answ = fnl_answ + "x" + (i+1) +" = " + fnl_result[i] + "  ";
                    }

                }
                System.out.println(fnl_answ);
                answer.setText(fnl_answ);
                export_excel.setDisable(false);
            }
        });

        import_excel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Node source = (Node) actionEvent.getSource();
                Stage primaryStage = (Stage) source.getScene().getWindow();
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx");
                fileChooser.getExtensionFilters().add(txtFilter);
                fileChooser.getExtensionFilters().addAll(txtFilter);
                fileChooser.setTitle("Вибір файлу");
                fileobject = fileChooser.showOpenDialog(primaryStage);

                try {
                    int j = 1;
                    int p = 0;
                    int x = 1;
                    XSSFRow row;

                    XSSFWorkbook wb = new XSSFWorkbook(new File(fileobject.getPath()));
                    XSSFSheet sheet = wb.getSheetAt(0);

                    spinner.getValueFactory().setValue(sheet.getLastRowNum());
                    row = sheet.getRow(j);
                    for (int i = 0; i < txt.length; i++) {
                        for (int k = 1; k < sheet.getLastRowNum(); k++) {
                            if ((i - p) == sheet.getLastRowNum()) {
                                p = sheet.getLastRowNum() * j++;
                                row = sheet.getRow(j);
                                System.out.println("pk" + j);
                                break;
                            }
                        }
                        txt[i].setText(String.valueOf(row.getCell(i - p).getNumericCellValue()));
                    }
                    row = sheet.getRow(x);
                    for (int i = 0; i < txt1.length; i++) {
                        if (txt1.length == sheet.getLastRowNum())
                            row = sheet.getRow(x++);
                        txt1[i].setText(String.valueOf(row.getCell(sheet.getLastRowNum()).getNumericCellValue()));
                    }
                    wb.close();
                } catch (IOException | InvalidFormatException e) {
                    e.printStackTrace();
                }

            }
        });


        export_excel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent){
                try {
                    XSSFRow row;
                    XSSFWorkbook wb = new XSSFWorkbook();
                    XSSFSheet sheet = wb.createSheet("1");
                    row = sheet.createRow(0);
                    row.createCell(0).setCellValue("Відповідь:");
                    row.createCell(1).setCellValue(fnl_answ);

                    Node source = (Node) actionEvent.getSource();
                    Stage primaryStage = (Stage) source.getScene().getWindow();
                    FileChooser fileChooser = new FileChooser();
                    FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx");
                    fileChooser.getExtensionFilters().add(txtFilter);
                    fileChooser.getExtensionFilters().addAll(txtFilter);
                    fileChooser.setTitle("Зберегти");

                    File file = fileChooser.showSaveDialog(stage);
                    fileChooser.setInitialDirectory(file.getParentFile());

                    FileOutputStream fileOut = new FileOutputStream(file.getPath());
                    wb.write(fileOut);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        export_excel.setText("Зберегти як...");
        export_excel.setLayoutX(705);
        export_excel.setLayoutY(540);
        root.getChildren().add(export_excel);

        import_excel.setText("Відкрити приклад...");
        import_excel.setLayoutX(580);
        import_excel.setLayoutY(540);
        root.getChildren().add(import_excel);


        count_unk.setLayoutX(25);
        count_unk.setLayoutY(20);
        root.getChildren().add(count_unk);

        answer.setLayoutX(25);
        answer.setLayoutY(425);
        root.getChildren().add(answer);

        label_answer.setLayoutX(25);
        label_answer.setLayoutY(400);
        root.getChildren().add(label_answer);

        root.getChildren().add(btn_solve);
        stage.setTitle("SLAR calculate");
        stage.setScene(this.scene);
        stage.show();

    }


}