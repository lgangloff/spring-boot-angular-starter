import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitStatusComponent } from './submit-status.component';

describe('SubmitStatusComponent', () => {
  let component: SubmitStatusComponent;
  let fixture: ComponentFixture<SubmitStatusComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubmitStatusComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitStatusComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
