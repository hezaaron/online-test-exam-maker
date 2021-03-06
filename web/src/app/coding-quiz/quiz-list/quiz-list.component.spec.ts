import { fakeAsync, ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { OktaAuthService, OKTA_CONFIG } from '@okta/okta-angular';
import { RouterModule } from '@angular/router';
import { QuizService } from '../service/quiz.service';
import { QuizModule } from '../coding-quiz.module';
import { QuizListComponent } from '../quiz-list/quiz-list.component';
import { quizServiceStub } from '../../shared/mock/mock-quiz-service';
import { oktaConfig } from '../../shared/config/okta-confiq';

let component: QuizListComponent;
let fixture: ComponentFixture<QuizListComponent>;
let page: Page;

describe( 'QuizListComponent tests', () => {
	beforeEach( async(() => {
		TestBed.configureTestingModule( {
			imports: [QuizModule, RouterModule.forRoot( [] )],
			providers: [
			  OktaAuthService,
			  { provide: OKTA_CONFIG, useValue: oktaConfig},
				{ provide: QuizService, useValue: quizServiceStub }
			],
		} )
			.compileComponents()
			.then( createComponent );
	} ) );

	it( 'should display quizzes', () => {
		expect( page.quizNameRows.length ).toBeGreaterThan( 0 );
		expect( page.quizDescRows.length ).toBeGreaterThan( 0 );
	} );

	it( 'first quiz should match first quiz', () => {
		const expectedQuiz = quizServiceStub.quizList[0];
		const actualQuizName = page.quizNameRows[0].textContent;
		const actualQuizDesc = page.quizDescRows[0].textContent;
		expect( actualQuizName ).toContain( expectedQuiz.name, 'JavaScript Quiz' );
		expect( actualQuizDesc ).toContain( expectedQuiz.description, 'JavaScript Beginner Test' );
	} );

	it( 'should render route as /:id', fakeAsync(() => {
		const link = fixture.debugElement.query( By.css( 'a' ) ).nativeElement.getAttribute( 'href' );
		expect( link ).toEqual( '/1/instructions' );
	} ) );

} );


function createComponent() {
	fixture = TestBed.createComponent( QuizListComponent );
	component = fixture.componentInstance;
	fixture.detectChanges();

	return fixture.whenStable().then(() => {
		fixture.detectChanges();
		page = new Page();
	} );
}

class Page {
	quizNameRows: HTMLHeadingElement[];
	quizDescRows: HTMLParagraphElement[];

	constructor() {
		const quizNameNodes = fixture.nativeElement.querySelectorAll( 'h6' );
		const quizDescNodes = fixture.nativeElement.querySelectorAll( 'p' );
		this.quizNameRows = Array.from( quizNameNodes );
		this.quizDescRows = Array.from( quizDescNodes );
	}
}
