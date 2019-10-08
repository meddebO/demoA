/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DemoATestModule } from '../../../test.module';
import { SocieteComponent } from 'app/entities/societe/societe.component';
import { SocieteService } from 'app/entities/societe/societe.service';
import { Societe } from 'app/shared/model/societe.model';

describe('Component Tests', () => {
  describe('Societe Management Component', () => {
    let comp: SocieteComponent;
    let fixture: ComponentFixture<SocieteComponent>;
    let service: SocieteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DemoATestModule],
        declarations: [SocieteComponent],
        providers: []
      })
        .overrideTemplate(SocieteComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SocieteComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SocieteService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Societe(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.societes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
