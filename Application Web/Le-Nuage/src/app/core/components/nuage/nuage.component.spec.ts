import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NuageComponent } from './nuage.component';

describe('NuageComponent', () => {
  let component: NuageComponent;
  let fixture: ComponentFixture<NuageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NuageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NuageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
