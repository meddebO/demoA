import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISociete, Societe } from 'app/shared/model/societe.model';
import { SocieteService } from './societe.service';

@Component({
  selector: 'jhi-societe-update',
  templateUrl: './societe-update.component.html'
})
export class SocieteUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    idsociete: [],
    libelle: [],
    adresse: []
  });

  constructor(protected societeService: SocieteService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ societe }) => {
      this.updateForm(societe);
    });
  }

  updateForm(societe: ISociete) {
    this.editForm.patchValue({
      id: societe.id,
      idsociete: societe.idsociete,
      libelle: societe.libelle,
      adresse: societe.adresse
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const societe = this.createFromForm();
    if (societe.id !== undefined) {
      this.subscribeToSaveResponse(this.societeService.update(societe));
    } else {
      this.subscribeToSaveResponse(this.societeService.create(societe));
    }
  }

  private createFromForm(): ISociete {
    return {
      ...new Societe(),
      id: this.editForm.get(['id']).value,
      idsociete: this.editForm.get(['idsociete']).value,
      libelle: this.editForm.get(['libelle']).value,
      adresse: this.editForm.get(['adresse']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISociete>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
