import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { LoginService } from './login.service';

describe('LoginService', () => {
  let service: LoginService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
       imports: [HttpClientTestingModule],
        providers: [LoginService]
      }).compileComponents();
    service = TestBed.inject(LoginService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

      it('should login a user', () => {

        const mockData = { "email":"user@gmail.com","password":"123" };
        service.RegisterUser(mockData);

        const req = httpMock.expectOne('http://localhost:8081/auth/register'); // Replace with the actual API endpoint

        expect(req.request.method).toBe('POST');

        req.flush(mockData); // Respond with mock data

        httpMock.verify(); // Verify that no outstanding requests are pending

      });


});
