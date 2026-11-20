import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprenantForm } from './apprenant-form';

describe('ApprenantForm', () => {
  let component: ApprenantForm;
  let fixture: ComponentFixture<ApprenantForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ApprenantForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ApprenantForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
