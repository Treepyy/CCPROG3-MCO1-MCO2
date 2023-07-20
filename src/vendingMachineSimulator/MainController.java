package vendingMachineSimulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class MainController {

    private MainView mainView;
    private MainModel mainModel;
    private String type;
    private int itemSelectionIndex;

    MainController(MainView mainView, MainModel mainModel){

        this.mainView = mainView;
        this.mainModel = mainModel;

        this.mainView.setExitBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        this.mainView.setCreateBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayCreate();
            }
        });

        this.mainView.setTestBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainModel.hasCreatedVM())
                    mainView.displayTest();
                else
                    mainView.displayError("Create a Vending Machine first!");
            }
        });

        this.mainView.setHomeBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayMain();
                mainView.clearFeedback();
            }
        });

        this.mainView.setRegularBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainModel.hasCreatedVM()){
                    mainModel.createRegularVM();
                    mainView.displayFeedback("Successfully created a Regular Vending Machine!", Color.GREEN);
                }
                else{
                    mainView.displayOverwriteConfirmation();
                    type = "Regular";
                }
            }
        });

        this.mainView.setOverwriteYesBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(type, "Regular"))
                    mainModel.createRegularVM();
                else
                    mainModel.createSpecialVM();

                mainView.closeErrorFrame();
                mainView.displayFeedback("Successfully created a " + type + " Vending Machine!", Color.GREEN);
            }
        });

        this.mainView.setOverwriteNoBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.closeErrorFrame();
                mainView.displayFeedback("Operation aborted.", Color.RED);
            }
        });

        this.mainView.setSpecialBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!mainModel.hasCreatedVM()){
                    mainModel.createRegularVM();
                    mainView.displayFeedback("Successfully created a Special Vending Machine!", Color.GREEN);
                }
                else{
                    mainView.displayOverwriteConfirmation();
                    type = "Special";
                }
            }
        });

        this.mainView.setOkayBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.closeErrorFrame();
            }
        });

        this.mainView.setMaintBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayMaint();
            }
        });

        this.mainView.setMaintReturnBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayTest();
            }
        });

        this.mainView.setOptionReturnBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displayMaint();
                mainView.clearFeedback();
            }
        });

        this.mainView.setSetPriceBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.setDropDownItems(mainModel.getItemNameList());
                mainView.displayProductSelection();
            }
        });

        this.mainView.setConfirmBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainView.displaySetPrice(mainModel.getItemPrice(mainView.getDropDownSelection()));
            }
        });

        this.mainView.setChangeBtnListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainModel.changeItemPrice(mainView.getDropDownSelection(), Double.parseDouble(mainView.getPriceTfText()));
                mainView.displayFeedback("Price Changed Successfully!", Color.GREEN);
                mainView.displayProductSelection();
            }
        });

    }

}
