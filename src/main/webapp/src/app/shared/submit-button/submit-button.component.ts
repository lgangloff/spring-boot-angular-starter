import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'submit-button',
  templateUrl: './submit-button.component.html',
  styleUrls: ['./submit-button.component.css']
})
export class SubmitButtonComponent implements OnInit {

  @Input('status')
  private status: string;

  @Input('value')
  private buttonValue = "Sauvegarder";

  @Input('disabled')
  private disabled = false;

  constructor() { }

  ngOnInit() {
  }

}
