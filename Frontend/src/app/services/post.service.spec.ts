import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PostService } from './post.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { MatSnackBarModule } from '@angular/material/snack-bar';



describe('PostService', () => {

  let service: PostService;

  let httpMock: HttpTestingController;



  beforeEach(() => {
      TestBed.configureTestingModule({

      imports: [HttpClientTestingModule,MatSnackBarModule],

      providers: [PostService,HttpClient], // Provide the service you want to test

    }).compileComponents();

    service = TestBed.inject(PostService);

    httpMock = TestBed.inject(HttpTestingController);

  });



  it('should be created', () => {
    expect(service).toBeTruthy();

  });



  it('should create a post', () => {

    const mockData = { "title":"new title","content":"content of post","image":"url" };
    service.createPost(mockData);

    const req = httpMock.expectOne('http://localhost:8081/posts'); // Replace with the actual API endpoint

    expect(req.request.method).toBe('POST');

    req.flush(mockData); // Respond with mock data

    httpMock.verify(); // Verify that no outstanding requests are pending

  });


  it('should delete a post', () => {

   const mockData = { "title":"new title","content":"content of post","image":"url" };
    service.deletePost('1234');

    const req = httpMock.expectOne('http://localhost:8081/posts/1234'); // Replace with the actual API endpoint

    expect(req.request.method).toBe('DELETE');

    req.flush(mockData); // Respond with mock data

    httpMock.verify(); // Verify that no outstanding requests are pending

  });

    it('should update a post', () => {

      const mockData = { "title":"new title","content":"content of post","image":"url" };
      service.updatePost('1234',mockData);

      const req = httpMock.expectOne('http://localhost:8081/posts/1234'); // Replace with the actual API endpoint

      expect(req.request.method).toBe('PUT');


      req.flush(mockData); // Respond with mock data

      httpMock.verify(); // Verify that no outstanding requests are pending

    });




});
