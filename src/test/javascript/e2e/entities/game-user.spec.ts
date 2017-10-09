import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';


describe('GameUser e2e test', () => {

    let navBarPage: NavBarPage;
    let gameUserDialogPage: GameUserDialogPage;
    let gameUserComponentsPage: GameUserComponentsPage;


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load GameUsers', () => {
        navBarPage.goToEntity('game-user');
        gameUserComponentsPage = new GameUserComponentsPage();
        expect(gameUserComponentsPage.getTitle()).toMatch(/jhipsterApp.gameUser.home.title/);

    });

    it('should load create GameUser dialog', () => {
        gameUserComponentsPage.clickOnCreateButton();
        gameUserDialogPage = new GameUserDialogPage();
        expect(gameUserDialogPage.getModalTitle()).toMatch(/jhipsterApp.gameUser.home.createOrEditLabel/);
        gameUserDialogPage.close();
    });

    it('should create and save GameUsers', () => {
        gameUserComponentsPage.clickOnCreateButton();
        gameUserDialogPage.userSelectLastOption();
        gameUserDialogPage.matchSelectLastOption();
        gameUserDialogPage.save();
        expect(gameUserDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class GameUserComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-game-user div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GameUserDialogPage {
    modalTitle = element(by.css('h4#myGameUserLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    userSelect = element(by.css('select#field_user'));
    matchSelect = element(by.css('select#field_match'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    userSelectLastOption = function () {
        this.userSelect.all(by.tagName('option')).last().click();
    }

    userSelectOption = function (option) {
        this.userSelect.sendKeys(option);
    }

    getUserSelect = function () {
        return this.userSelect;
    }

    getUserSelectedOption = function () {
        return this.userSelect.element(by.css('option:checked')).getText();
    }

    matchSelectLastOption = function () {
        this.matchSelect.all(by.tagName('option')).last().click();
    }

    matchSelectOption = function (option) {
        this.matchSelect.sendKeys(option);
    }

    getMatchSelect = function () {
        return this.matchSelect;
    }

    getMatchSelectedOption = function () {
        return this.matchSelect.element(by.css('option:checked')).getText();
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
