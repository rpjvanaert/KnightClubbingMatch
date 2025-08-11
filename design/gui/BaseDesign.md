# Design of KnightClubbingMatch - GUI

GUI application for managing and playing KnightClubbing/UCI matches.

<!-- TOC -->
* [Design of KnightClubbingMatch - GUI](#design-of-knightclubbingmatch---gui)
  * [Overview](#overview)
    * [Layout](#layout)
<!-- TOC -->

## Overview

### Layout
The GUI is designed to be easy to use and navigate. It consists of the following main components
- **Main menu**: provides access to different functionalities such as running matches, viewing history, and managing engines.
- **History**: displays a list of past matches with options to filter and search.
- **Engine management**: allows users to add and remove engines, and view their settings.
- **Match setup**: allows user to configure match parameters such as time control, engine settings, SPRT or not and other options.
- **Live match view**: shows running match, their status, and allows users to join as spectators.
- **Live match spectator**: displays the current match status, board position, and moves in real-time.
- **Results**: shows the results of completed matches, including SPRT statistics and PGN files.
