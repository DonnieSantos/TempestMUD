#include <stdio.h>
#include <iostream.h>
#include "../headers/includes.h"

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::blink()

{
  echo("You blink twice to clear your eyes.");
  blind_emote("blinks innocently.");
}

void entity::blink(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You lower your head and blink to yourself.");
    blind_emote("lowers " + his + " head and blinks to " + himself + "."); }

  else if (ENT != NULL) {
    echo("You look at " + ENT->get_name() + " and blink in wonder.");
    ENT->echo(name + " looks at you and blinks in wonder.");
    xblind_emote(ENT, "looks at " + ENT->get_name() + " and blinks in wonder."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::nod()

{
  echo("You nod solemnly.");
  blind_emote("nods solemnly.");
}

void entity::nod(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You nod thoughtfully to yourself.");
    blind_emote("nods " + his + " head thoughtfully to " + himself + "."); }

  else if (ENT != NULL) {
    echo("You nod in recognition to " + ENT->get_name() + ".");
    ENT->echo(name + " nods in recognition to you.");
    xblind_emote(ENT, "nods in recognition to " + ENT->get_name() + "."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::laugh()

{
  echo("You hold your stomach with laughter.");
  blind_emote("laughs.");
}

void entity::laugh(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You laugh at your own foolish behavior.");
    blind_emote("laughs at " + his + " own foolish behavior."); }

  else if (ENT != NULL) {
    echo("You laugh loudly at " + ENT->get_name() + ".");
    ENT->echo(name + " laughs loudly at you.");
    xblind_emote(ENT, "laughs loudly at " + ENT->get_name() + "."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::cackle()

{
  echo("You cackle with a touch of insanity.");
  blind_emote("throws " + his + " head back and cackles with insane glee!");
}

void entity::cackle(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You cackle insanely, in marvel of your own genius.");
    blind_emote("cackles insanely, in marvel of " + his + " own genius."); }

  else if (ENT != NULL) {
    echo("You cackle insanely at " + ENT->get_name() + ".");
    ENT->echo(name + " cackles insanely at you.");
    xblind_emote(ENT, "cackles insanely at " + ENT->get_name() + "."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::shake()

{
  echo("You shake your head.");
  blind_emote("shakes " + his + " head.");
}

void entity::shake(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You shake your head to yourself in disgust.");
    blind_emote("shakes " + his + " head to " + himself + " in disgust."); }

  else if (ENT != NULL) {
    echo("You give " + ENT->get_name() + " a firm handshake.");
    ENT->echo(name + " gives you a firm handshake.");
    xblind_emote(ENT, "gives " + ENT->get_name() + " a firm handshake."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::smile()

{
  echo("You smile brightly.");
  blind_emote("smiles brightly.");
}

void entity::smile(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You smile happily to yourself.");
    blind_emote("smiles happily to " + himself + "."); }

  else if (ENT != NULL) {
    echo("You smile brightly at " + ENT->get_name() + ".");
    ENT->echo(name + " smiles brightly at you.");
    xblind_emote(ENT, "smiles brightly at " + ENT->get_name() + "."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::grin()

{
  echo("You grin deviously.");
  blind_emote("grins deviously.");
}

void entity::grin(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You grin deviously to yourself.");
    blind_emote("grins deviously to " + himself + "."); }

  else if (ENT != NULL) {
    echo("You grin deviously at " + ENT->get_name() + ".");
    ENT->echo(name + " grins deviously at you.");
    xblind_emote(ENT, "grins deviously at " + ENT->get_name() + "."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::sigh()

{
  echo("You sigh deeply.");
  blind_emote("sighs deeply.");
}

void entity::sigh(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You sigh sadly to yourself.");
    blind_emote("sighs sadly to " + himself + "."); }

  else if (ENT != NULL) {
    echo("You look at " + ENT->get_name() + " and sigh.");
    ENT->echo(name + " looks at you and sighs.");
    xblind_emote(ENT, "looks at " + ENT->get_name() + " and sighs."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::shrug()

{
  echo("You shrug helplessly.");
  blind_emote("shrugs helplessly.");
}

void entity::shrug(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You shrug to yourself.");
    blind_emote("shrugs to " + himself + "."); }

  else if (ENT != NULL) {
    echo("You look at " + ENT->get_name() + " and shrug.");
    ENT->echo(name + " looks at you and shrugs.");
    xblind_emote(ENT, "looks at " + ENT->get_name() + " and shrugs."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::peer()

{
  echo("You peer curiously.");
  blind_emote("peers curiously.");
}

void entity::peer(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You lower your head and peer curiously.");
    blind_emote("lowers " + his + " head and peers curiously."); }

  else if (ENT != NULL) {
    echo("You peer curiously at " + ENT->get_name() + ".");
    ENT->echo(name + " peers curiously at you.");
    xblind_emote(ENT, "peers curiously at " + ENT->get_name() + "."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::frown()

{
  echo("You frown.");
  blind_emote("frowns.");
}

void entity::frown(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You lower your head and frown.");
    blind_emote("lowers " + his + " head and frowns."); }

  else if (ENT != NULL) {
    echo("You frown at " + ENT->get_name() + ".");
    ENT->echo(name + " frowns at you.");
    xblind_emote(ENT, "frowns at " + ENT->get_name() + "."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::growl()

{
  echo("Grrrrrrrr...");
  blind_emote("growls angrily.");
}

void entity::growl(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You growl angrily at yourself.");
    blind_emote("growls angrily at " + himself + "."); }

  else if (ENT != NULL) {
    echo("You growl angrily at " + ENT->get_name() + ".");
    ENT->echo(name + " growls angrily at you.");
    xblind_emote(ENT, "growls angrily at " + ENT->get_name() + "."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::snarl()

{
  echo("You snarl like a vicious animal.");
  blind_emote("snarls like a vicious animal.");
}

void entity::snarl(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You snarl viciously at yourself.");
    blind_emote("snarls viciously at " + himself + "."); }

  else if (ENT != NULL) {
    echo("You snarl viciously at " + ENT->get_name() + ".");
    ENT->echo(name + " snarls viciously at you.");
    xblind_emote(ENT, "snarls viciously at " + ENT->get_name() + "."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::tip()

{
  echo("You tip your hat gracefully.");
  blind_emote("tips " + his + " hat gracefully.");
}

void entity::tip(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You proudly tip your hat to yourself.");
    blind_emote("proudly tips " + his + " hat to " + himself + "."); }

  else if (ENT != NULL) {
    echo("You gracefully tip your hat to " + ENT->get_name() + ".");
    ENT->echo(name + " gracefully tips " + his + " hat to you.");
    xblind_emote(ENT, "gracefully tips " + his + " hat to " + ENT->get_name() + "."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //

void entity::giggle()

{
  echo("You cover your face and giggle shyly.");
  blind_emote("blushes slightly and giggles to " + himself + ".");
}

void entity::giggle(string target)

{
  entity *ENT = rm->find_entity(this, target);

  if (ENT == this) {
    echo("You giggle softly to yourself.");
    blind_emote("giggles softly to " + himself + "."); }

  else if (ENT != NULL) {
    echo("You giggle at " + ENT->get_name() + ". What a big silly.");
    ENT->echo(name + " giggles at your silliness.");
    xblind_emote(ENT, "giggles at " + ENT->get_name() + ". What a big silly."); }

  else echo("There's nobody here by that name.");
}

// ***************************************************************************************** //
// ***************************************************************************************** //