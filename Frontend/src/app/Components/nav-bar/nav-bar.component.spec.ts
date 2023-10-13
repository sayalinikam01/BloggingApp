import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LoginService } from '../../services/login.service';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import {MatToolbarModule} from '@angular/material/toolbar';
import { RouterTestingModule } from '@angular/router/testing';
import { NavBarComponent } from './nav-bar.component';
import { MatIconModule } from '@angular/material/icon';

describe('NavBarComponent', () => {
  let component: NavBarComponent;
  let fixture: ComponentFixture<NavBarComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [NavBarComponent],
      imports:[HttpClientTestingModule,RouterTestingModule,MatToolbarModule,MatIconModule],
      providers:[LoginService]
    });
    fixture = TestBed.createComponent(NavBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
