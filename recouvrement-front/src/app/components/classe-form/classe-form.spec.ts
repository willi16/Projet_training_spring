import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClasseForm } from './classe-form';

describe('ClasseForm', () => {
  let component: ClasseForm;
  let fixture: ComponentFixture<ClasseForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClasseForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClasseForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
