import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Match e2e test', () => {

    let navBarPage: NavBarPage;
    let matchDialogPage: MatchDialogPage;
    let matchComponentsPage: MatchComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Matches', () => {
        navBarPage.goToEntity('match');
        matchComponentsPage = new MatchComponentsPage();
        expect(matchComponentsPage.getTitle()).toMatch(/jhipsterApp.match.home.title/);

    });

    it('should load create Match dialog', () => {
        matchComponentsPage.clickOnCreateButton();
        matchDialogPage = new MatchDialogPage();
        expect(matchDialogPage.getModalTitle()).toMatch(/jhipsterApp.match.home.createOrEditLabel/);
        matchDialogPage.close();
    });

    it('should create and save Matches', () => {
        matchComponentsPage.clickOnCreateButton();
        matchDialogPage.setInitialSeedInput('5');
        expect(matchDialogPage.getInitialSeedInput()).toMatch('5');
        matchDialogPage.save();
        expect(matchDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class MatchComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-match div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class MatchDialogPage {
    modalTitle = element(by.css('h4#myMatchLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    initialSeedInput = element(by.css('input#field_initialSeed'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setInitialSeedInput = function (initialSeed) {
        this.initialSeedInput.sendKeys(initialSeed);
    }

    getInitialSeedInput = function () {
        return this.initialSeedInput.getAttribute('value');
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
