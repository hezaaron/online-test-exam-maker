import { Component, OnInit } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { ExamService } from '../service/exam.service';

@Component( {
	selector: 'app-exam-results',
	template: `
      <div class="container">
        <ng-container *ngIf="stats; else loading">
          <h5 class="text-sm-center blue-grey-text">{{stats.title}}</h5>
          <div class="mt-4">
            <table class="table">
              <tr><td>Start:</td><td>{{stats.startTime}}</td></tr> 
              <tr><td>Finish:</td><td>{{stats.finishTime}}</td></tr>
              <tr><td class="text-nowrap">Number of Questions:</td><td>{{stats.questionCount}}</td></tr>
              <tr><td>Score:</td><td [ngClass]="{'text-success': stats.grade >= 50, 'text-danger': stats.grade < 50}">{{stats.grade}}</td></tr>
              <tr><td>Max Score:</td><td>{{stats.maxGrade}}</td></tr> 
            </table>
          </div>
				</ng-container>
				<ng-template #loading>Loading results...</ng-template>
      </div>`
} )
export class ExamResultsComponent implements OnInit {
	stats: Array<any>;
	resultId: number;
	private subscription: Subscription;

	constructor( private examService: ExamService ) { }

	ngOnInit() {
		this.subscription = this.examService.resultId.subscribe( value => {
			this.resultId = value;
		},
			error => console.error( error ) );

		this.examService.getExamStat( this.resultId ).subscribe( stats => {
			this.stats = stats;
		} );
	}

	ngOnDestroy() {
		this.subscription.unsubscribe();
	}
}
