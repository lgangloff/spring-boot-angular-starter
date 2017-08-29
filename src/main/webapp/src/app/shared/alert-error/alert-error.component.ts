import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'alert-error',
  templateUrl: './alert-error.component.html',
  styleUrls: ['./alert-error.component.css']
})
export class AlertErrorComponent implements OnInit {

  @Input("error")
  private error;

  constructor() { }

  ngOnInit() {
  }

}
