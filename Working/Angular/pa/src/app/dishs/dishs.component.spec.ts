import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DishsComponent } from './dishs.component';

describe('DishsComponent', () => {
  let component: DishsComponent;
  let fixture: ComponentFixture<DishsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DishsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DishsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
