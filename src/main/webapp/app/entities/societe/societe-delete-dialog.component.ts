import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISociete } from 'app/shared/model/societe.model';
import { SocieteService } from './societe.service';

@Component({
  selector: 'jhi-societe-delete-dialog',
  templateUrl: './societe-delete-dialog.component.html'
})
export class SocieteDeleteDialogComponent {
  societe: ISociete;

  constructor(protected societeService: SocieteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.societeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'societeListModification',
        content: 'Deleted an societe'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-societe-delete-popup',
  template: ''
})
export class SocieteDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ societe }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SocieteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.societe = societe;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/societe', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/societe', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
