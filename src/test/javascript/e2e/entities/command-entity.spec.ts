import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('CommandEntity e2e test', () => {

    let navBarPage: NavBarPage;
    let commandEntityDialogPage: CommandEntityDialogPage;
    let commandEntityComponentsPage: CommandEntityComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CommandEntities', () => {
        navBarPage.goToEntity('command-entity');
        commandEntityComponentsPage = new CommandEntityComponentsPage();
        expect(commandEntityComponentsPage.getTitle()).toMatch(/jhipsterApp.commandEntity.home.title/);

    });

    it('should load create CommandEntity dialog', () => {
        commandEntityComponentsPage.clickOnCreateButton();
        commandEntityDialogPage = new CommandEntityDialogPage();
        expect(commandEntityDialogPage.getModalTitle()).toMatch(/jhipsterApp.commandEntity.home.createOrEditLabel/);
        commandEntityDialogPage.close();
    });

    it('should create and save CommandEntities', () => {
        commandEntityComponentsPage.clickOnCreateButton();
        commandEntityDialogPage.setColorInput('color');
        expect(commandEntityDialogPage.getColorInput()).toMatch('color');
        commandEntityDialogPage.setPlayInput('play');
        expect(commandEntityDialogPage.getPlayInput()).toMatch('play');
        commandEntityDialogPage.setDiscardInput('discard');
        expect(commandEntityDialogPage.getDiscardInput()).toMatch('discard');
        commandEntityDialogPage.userSelectLastOption();
        commandEntityDialogPage.matchSelectLastOption();
        commandEntityDialogPage.save();
        expect(commandEntityDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CommandEntityComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-command-entity div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CommandEntityDialogPage {
    modalTitle = element(by.css('h4#myCommandEntityLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    colorInput = element(by.css('input#field_color'));
    playInput = element(by.css('input#field_play'));
    discardInput = element(by.css('input#field_discard'));
    userSelect = element(by.css('select#field_user'));
    matchSelect = element(by.css('select#field_match'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setColorInput = function (color) {
        this.colorInput.sendKeys(color);
    }

    getColorInput = function () {
        return this.colorInput.getAttribute('value');
    }

    setPlayInput = function (play) {
        this.playInput.sendKeys(play);
    }

    getPlayInput = function () {
        return this.playInput.getAttribute('value');
    }

    setDiscardInput = function (discard) {
        this.discardInput.sendKeys(discard);
    }

    getDiscardInput = function () {
        return this.discardInput.getAttribute('value');
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
