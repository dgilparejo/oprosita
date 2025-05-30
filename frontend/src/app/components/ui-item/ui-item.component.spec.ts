import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UiItemComponent } from './ui-item.component';

describe('UiItemComponent', () => {
  let component: UiItemComponent;
  let fixture: ComponentFixture<UiItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UiItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UiItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
