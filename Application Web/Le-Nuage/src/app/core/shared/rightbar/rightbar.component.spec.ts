import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RightbarComponent } from './rightbar.component';

describe('RightbarComponent', () => {
  let component: RightbarComponent;
  let fixture: ComponentFixture<RightbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RightbarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RightbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
