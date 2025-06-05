import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SendbirdWidgetComponent } from './sendbird-widget.component';

describe('SendbirdWidgetComponent', () => {
  let component: SendbirdWidgetComponent;
  let fixture: ComponentFixture<SendbirdWidgetComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SendbirdWidgetComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SendbirdWidgetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
