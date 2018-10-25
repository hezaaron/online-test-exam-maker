import { Component, OnInit } from '@angular/core';
import { ExamService } from '../shared/exam/exam.service';

@Component({
  selector: 'app-exam-list',
  templateUrl: './exam-list.component.html',
  styleUrls: ['./exam-list.component.css']
})
export class ExamListComponent implements OnInit {
    
    exams: Array<any>;

  constructor(private examService: ExamService) { }
  
  trackById(index, exam) {
      return exam.id;
  }

  ngOnInit() {
      this.examService.getAll().subscribe(data => {
          this.exams = data;
      },
      error => console.error(error))
  }

}
